package me.magicall.game.sanguosha.core.area;

import me.magicall.game.card.Card;
import me.magicall.game.sanguosha.core.card.CardTypes;

import java.util.Collection;

/**
 * @author Liang Wenjian
 */
public class JudgementArea extends AbsArea {

    public JudgementArea() {
        super("判定区");
    }

    @Override
    public boolean canGain(final Collection<Card> cards) {
        //判定区里，只能放延时锦囊，并且同名的只能贴一张。
        final Collection<Card> owning = getCards();
        for (final Card c : cards) {
            if (c.getType() != CardTypes.延时锦囊) {
                return false;
            }
            for (final Card card : owning) {
                if (card.getName().equals(c.getName())) {
                    return false;
                }
            }
        }
        return true;
    }
}
