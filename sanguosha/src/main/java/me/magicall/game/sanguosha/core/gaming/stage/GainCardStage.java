package me.magicall.game.sanguosha.core.gaming.stage;

import me.magicall.game.card.Card;
import me.magicall.game.sanguosha.core.area.Area;
import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.Collection;

/**
 * 摸牌阶段。
 * 摸牌阶段：角色摸两张牌。
 *
 * @author Liang Wenjian
 */
public class GainCardStage extends AbsStage {

    private static final int GAIN_COUNT = 2;

    public GainCardStage(final Sanguosha game, final Hero hero) {
        super(game, hero);
    }

    @Override
    protected void playInternal() {
        final Collection<Card> cards = getGame().cardsStackPop(GAIN_COUNT);
        final Area hand = getOwner().getHand();
        if (hand.canGain(cards)) {
            hand.gain(cards);
        }
    }
}
