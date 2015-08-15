package me.magicall.game.sanguosha.core.gaming;

import me.magicall.game.card.Card;
import me.magicall.game.card.Event;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.Collection;

/**
 * @author Liang Wenjian
 */
public class 弃置牌Event extends Event {

    private static final long serialVersionUID = 8440721227409317585L;

    private Collection<Card> cards;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public 弃置牌Event(final Hero source, final Collection<Card> cards) {
        super(source);
        this.cards = cards;
    }

    @Override
    public Hero getSource() {
        return (Hero) super.getSource();
    }

    public Collection<Card> getCards() {
        return cards;
    }
}
