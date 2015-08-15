package me.magicall.game.sanguosha.core.gaming;

import me.magicall.game.card.Event;
import me.magicall.game.sanguosha.core.unit.Hero;

/**
 * @author Liang Wenjian
 */
public class HarmingEvent extends Event {

    private static final long serialVersionUID = 7543895263806117841L;
    
    private int count;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     * @param count
     */
    public HarmingEvent(final Hero source, final int count) {
        super(source);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }
}
