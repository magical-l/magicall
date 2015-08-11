package me.magicall.game.sanguosha.core.gaming.stage;

import me.magicall.game.card.Card;
import me.magicall.game.sanguosha.core.area.Area;
import me.magicall.game.sanguosha.core.area.CardStack;
import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.hero.Hero;

import java.util.Collection;

/**
 * @author Liang Wenjian
 */
public class GainCardStage extends AbsStage {

    public GainCardStage(final Sanguosha game, final Hero hero) {
        super(game, hero);
    }

    @Override
    protected void playInternal() {
        final Sanguosha game = getGame();
        final CardStack cardStack = game.getCardStack();
        final Collection<Card> cards = cardStack.pop(2);
        final Hero owner = getOwner();
        final Area hand = owner.getHand();
        hand.gain(cards);
    }
}
