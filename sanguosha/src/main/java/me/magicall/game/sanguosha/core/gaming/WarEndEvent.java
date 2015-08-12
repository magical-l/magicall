package me.magicall.game.sanguosha.core.gaming;

import me.magicall.game.card.Event;

/**
 * @author Liang Wenjian
 */
public class WarEndEvent extends Event {

    private static final long serialVersionUID = -5204914756415267698L;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public WarEndEvent(final Sanguosha source) {
        super(source);
    }
}
