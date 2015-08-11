package me.magicall.game.sanguosha.core.area;

import me.magicall.game.card.Card;
import me.magicall.mark.Named;

import java.util.Collection;
import java.util.List;

/**
 * 牌区。
 *
 * @author Liang Wenjian
 */
public interface Area extends Named {

    List<Card> getCards();

    boolean canGain(final Collection<Card> cards);

    default int getCardsCount() {
        return getCards().size();
    }

    /**
     * 获得牌
     *
     * @param cards
     */
    default void gain(final Collection<Card> cards) {
        getCards().addAll(cards);
    }

    /**
     * 弃置牌
     *
     * @param cards
     */
    default void discard(final Collection<Card> cards) {
        getCards().removeAll(cards);
    }

    /**
     * 使用牌
     *
     * @param cards
     */
    default void use(final Collection<Card> cards) {
        getCards().removeAll(cards);
    }
}
