package me.magicall.game.sanguosha.core.gaming.stage;

import me.magicall.game.card.Card;
import me.magicall.game.sanguosha.core.area.Area;
import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.hero.Hero;

import java.util.List;

/**
 * @author Liang Wenjian
 */
public class DiscardStage extends AbsStage {

    public DiscardStage(final Sanguosha game, final Hero hero) {
        super(game, hero);
    }

    @Override
    protected void playInternal() {
        final Hero owner = getOwner();
        final Area hand = owner.getHand();
        final List<Card> cards = hand.getCards();
        final int hp = owner.getHp();
        final int cardsCount = cards.size();
        if (cardsCount > hp) {
            owner.getPlayer().getPlayer().requireInput("请弃牌", null);//TODO
        }
    }
}
