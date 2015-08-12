package me.magicall.game.sanguosha.core.gaming;

import me.magicall.game.card.Card;
import me.magicall.game.card.Event;

import java.util.List;

/**
 * @author Liang Wenjian
 */
public class GetUsableCardsEvent extends Event {

    private static final long serialVersionUID = -6377212277299393223L;

    private final List<Card> cards;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     * @param cards
     */
    public GetUsableCardsEvent(final Object source, final List<Card> cards) {
        super(source);
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }
}
