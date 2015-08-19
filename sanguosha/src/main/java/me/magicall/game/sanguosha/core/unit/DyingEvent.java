package me.magicall.game.sanguosha.core.unit;

import me.magicall.game.card.Event;

/**
 * 濒死事件
 *
 * @author Liang Wenjian
 */
public class DyingEvent extends Event {

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
