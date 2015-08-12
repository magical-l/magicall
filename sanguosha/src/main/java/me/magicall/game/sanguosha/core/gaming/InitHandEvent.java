package me.magicall.game.sanguosha.core.gaming;

import me.magicall.game.card.Event;

/**
 * @author Liang Wenjian
 */
public class InitHandEvent extends Event {

    private static final long serialVersionUID = -5983133937430611201L;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public InitHandEvent(final Sanguosha source) {
        super(source);
    }
}
