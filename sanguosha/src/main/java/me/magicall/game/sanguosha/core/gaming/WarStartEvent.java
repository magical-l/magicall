package me.magicall.game.sanguosha.core.gaming;

import me.magicall.game.card.Event;

/**
 * @author Liang Wenjian
 */
public class WarStartEvent extends Event {

    private static final long serialVersionUID = 5962109857007073211L;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public WarStartEvent(final Sanguosha source) {
        super(source);
    }
}
