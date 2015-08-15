package me.magicall.game.sanguosha.core.card.cfg;

import me.magicall.game.card.Card;
import me.magicall.game.card.CardCfg;
import me.magicall.game.sanguosha.core.card.CardTypes;
import me.magicall.game.sanguosha.core.card.Flower;
import me.magicall.game.sanguosha.core.gaming.CardSelection;
import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.gaming.option.Options;
import me.magicall.game.sanguosha.core.player.GamingPlayer;
import me.magicall.game.sanguosha.core.skill.Skill;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Liang Wenjian
 */
public class 万箭齐发 extends CardCfg {

    public 万箭齐发(final int point, final Flower flower) {
        super(CardTypes.IMMEDIATE_JINNANG, point, flower, new Skill_万箭齐发());
    }

    private static class Skill_万箭齐发 implements Skill {
        @Override
        public void action(final Sanguosha game, final Hero user, final List<Hero> targets) {
            targets.forEach(hero -> {
                final GamingPlayer player = hero.getPlayer();
                final CardSelection selection = player.requireInput(new Select闪Options(player));
                if (selection == null) {
                    //没出闪，掉血。
                    game.harm(hero, null);
                } else {
                    final Integer cardId = selection.getCardId();
                    final Card card = game.getCard(cardId);
                    player.getHero().getHand().use(Collections.singleton(card));
                }
            });
        }

        @Override
        public List<Hero> selectTargets(final Sanguosha game, final Hero user) {
            return game.getSurvivors().stream().map(GamingPlayer::getHero).collect(Collectors.toList());
        }

        @Override
        public String getName() {
            return null;
        }

        private static class Select闪Options implements Options<CardSelection> {
            private final GamingPlayer player;

            public Select闪Options(final GamingPlayer player) {
                this.player = player;
            }

            @Override
            public String getMsg() {
                return "请打出一张闪。";
            }

            @Override
            public List<?> getOptions() {
                return player.getHero().getHand().getCards().stream()//
                        .filter(c -> "闪".equals(c.getName()))//
                        .collect(Collectors.toList());
            }

            @Override
            public CardSelection toSelection(final String input) {
                return input == null || input.trim().isEmpty() ? null : new CardSelection(Integer.parseInt(input));
            }
        }
    }
}
