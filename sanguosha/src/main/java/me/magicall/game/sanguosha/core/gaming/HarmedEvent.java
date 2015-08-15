package me.magicall.game.sanguosha.core.gaming;

import me.magicall.game.card.Event;
import me.magicall.game.sanguosha.core.unit.Hero;

/**
 * @author Liang Wenjian
 */
public class HarmedEvent extends Event {

    private static final long serialVersionUID = -33941726552927848L;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public HarmedEvent(final Hero source) {
        super(source);
    }
}
