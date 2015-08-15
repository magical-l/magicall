package me.magicall.game.sanguosha.core.unit;

import me.magicall.game.card.Event;
import me.magicall.game.sanguosha.core.unit.Hero;

/**
 * @author Liang Wenjian
 */
public class DyingEvent extends Event {

    private static final long serialVersionUID = 4031030490082681721L;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public DyingEvent(final Hero source) {
        super(source);
    }

    @Override
    public Hero getSource() {
        return (Hero) super.getSource();
    }
}
