package me.magicall.game.card;

import org.springframework.context.ApplicationEvent;

/**
 * @author Liang Wenjian
 */
public class Event extends ApplicationEvent{
    private static final long serialVersionUID = -2683867746284370387L;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public Event(final Object source) {
        super(source);
    }


}
