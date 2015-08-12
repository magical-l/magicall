package me.magicall.game.sanguosha.core.gaming;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.magicall.game.card.Card;
import me.magicall.game.card.CardCfg;
import me.magicall.game.card.Event;
import me.magicall.game.card.Game;
import me.magicall.game.card.Round;
import me.magicall.game.sanguosha.core.area.Battle;
import me.magicall.game.sanguosha.core.area.CardStack;
import me.magicall.game.sanguosha.core.area.HandArea;
import me.magicall.game.sanguosha.core.area.UsedCardStack;
import me.magicall.game.sanguosha.core.card.GamingCard;
import me.magicall.game.sanguosha.core.gaming.option.SelectHeroOptions;
import me.magicall.game.sanguosha.core.gaming.round.SanguoshaRound;
import me.magicall.game.sanguosha.core.player.GamingPlayer;
import me.magicall.game.sanguosha.core.player.Player;
import me.magicall.game.sanguosha.core.player.Role;
import me.magicall.game.sanguosha.core.unit.Hero;
import me.magicall.game.sanguosha.core.unit.HeroCfg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Liang Wenjian
 */
public class Sanguosha implements Game {

    //------------------------ 工具

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ApplicationEventPublisher publisher;

    //------------------------ 配置

    private final GamingCfg cfg;
    
    //------------------------ 初始化数据

    /**
     * 玩家列表。
     */
    private final List<GamingPlayer> players = Lists.newArrayList();

    /**
     * 武将备选记录。
     */
    private final ListMultimap<GamingPlayer, HeroCfg> heroOptions = ArrayListMultimap.create();

    private final List<HeroCfg> heroCfgs;

    private final Map<Integer, GamingCard> idCardMap = Maps.newHashMap();

    //------------------------ 游戏
    /**
     * 牌堆。
     */
    private final CardStack cardStack = new CardStack();
    /**
     * 结算区。
     */
    private final Battle battle = new Battle();
    /**
     * 弃牌堆。
     */
    private final UsedCardStack usedCardStack = new UsedCardStack();

    /**
     * 轮次。
     */
    private final List<Round> rounds = Lists.newArrayList();

    private boolean gameOver;

    //===============================================

    public Sanguosha(final ApplicationEventPublisher publisher, final GamingCfg cfg) {
        this.publisher = publisher;
        this.cfg = cfg;
        final List<HeroCfg> tmp = Lists.newArrayList(cfg.getHeroCfgs());
        Collections.shuffle(tmp);
        heroCfgs = tmp;
    }

    @Override
    public void play() {
        initCardStack();
        initPosition();

        selectHero();

        initHand();

        start();
    }

    private void start() {
        publishEvent(new WarStartEvent(this));
        int roundIndex = 0;
        while (!gameOver) {
            final Round round = new SanguoshaRound(this, roundIndex);
            round.play();
            rounds.add(round);
            roundIndex++;
        }
        publishEvent(new WarEndEvent(this));
    }

    private void initHand() {
        players.forEach(player -> {
            final Collection<Card> cards = cardStack.pop(4);
            final HandArea hand = player.getHero().getHand();
            if (hand.canGain(cards)) {
                hand.gain(cards);
            } else {
                cardStack.gain(cards);
            }
        });
        publishEvent(new InitHandEvent(this));
    }

    private void selectHero() {
        final Map<GamingPlayer, HeroCfg> map = Maps.newLinkedHashMap();
        //选将
        players.forEach(player -> {
            final HeroCfg selected = player.getPlayer()//
                    .requireInput(new SelectHeroOptions(getOptionHeros(player)))//
                    .getHeroCfg();
            player.setHero(new Hero(selected, this, player, player.getPosition()));
            final List<HeroCfg> options = heroOptions.get(player);
            options.remove(selected);
            heroCfgs.addAll(options);//没有选的武将还回武将堆。
            map.put(player, selected);
        });
        //亮武将
        players.forEach(player -> player.getPlayer().output(map));
        publishEvent(new HerosShownEvent(this, map));
    }

    public int getCardStackSize() {
        return cardStack.getCards().size();
    }

    public int getRoundCount() {
        return rounds.size() + 1;
    }

    public void publishEvent(final Event event) {
        publisher.publishEvent(event);
    }

    public int calculateDistance(final Hero from, final Hero to) {
        publishEvent(new CalculateDistanceEvent(this, from));

        final int distance = from.getCoordinate().distance(to.getCoordinate());

        return distance;//TODO:没算死人呢。
    }

    private List<HeroCfg> getOptionHeros(final GamingPlayer player) {
        final int size = 3;
        final List<HeroCfg> rt = Lists.newArrayListWithExpectedSize(size);
        for (int i = 0; i < size; i++) {
            final HeroCfg removed = heroCfgs.remove(i);
            rt.add(removed);
        }
        heroOptions.putAll(player, rt);
        return rt;
    }

    private void initPosition() {
        final List<Player> players = Lists.newArrayList(cfg.getPlayers());
        final int size = players.size();

        final Map<Role, Integer> countOfRole = cfg.getCountOfRole();
        final List<Role> tmpRoles = Lists.newArrayListWithExpectedSize(size);
        for (final Entry<Role, Integer> entry : countOfRole.entrySet()) {
            final Role role = entry.getKey();
            final Integer count = entry.getValue();
            for (int i = 0; i < count; i++) {
                tmpRoles.add(role);
            }
        }
        if (size != tmpRoles.size()) {
            throw new IllegalStateException("玩家和角色不一样多。");
        }
        Collections.shuffle(tmpRoles);

        if (!cfg.isPositionFixed()) {
            Collections.shuffle(players);
        }
        int i = 1;
        for (final Player player : players) {
            final Position position = new Position(i);
            final GamingPlayer p = new GamingPlayer();
            p.setRole(tmpRoles.get(i));
            p.setPlayer(player);
            p.setPosition(position);
            this.players.add(p);
            i++;
        }
        logger.debug(players.toString());
    }

    private void initCardStack() {
        final Map<CardCfg, Integer> cardCfgCount = cfg.getCountOfCardCfg();
        int id = 1;
        final List<Card> cards = Lists.newArrayList();
        for (final Entry<CardCfg, Integer> entry : cardCfgCount.entrySet()) {
            final CardCfg cardCfg = entry.getKey();
            final Integer count = entry.getValue();
            for (int i = 0; i < count; ++i) {
                final GamingCard card = new GamingCard(id, cardCfg);
                cards.add(card);
                idCardMap.put(id, card);
                id++;
            }
        }
        Collections.shuffle(cards);
        cardStack.gain(cards);

        logger.debug("initCardStack end:" + cardStack);
    }

    public List<HeroCfg> getHeroCfgs() {
        return heroCfgs;
    }

    public CardStack getCardStack() {
        return cardStack;
    }

    public Battle getBattle() {
        return battle;
    }

    public UsedCardStack getUsedCardStack() {
        return usedCardStack;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(final boolean gameOver) {
        this.gameOver = gameOver;
    }

    @Override
    public List<GamingPlayer> getPlayers() {
        return players;
    }

    public void cardWork(final Card card) {
        //TODO
    }

    public Card getCard(final Integer cardId) {
        return idCardMap.get(cardId);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + hashCode() + ":{" +
                "cfg:" + cfg +
                '}';
    }
}
