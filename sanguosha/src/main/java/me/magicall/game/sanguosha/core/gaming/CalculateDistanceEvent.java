package me.magicall.game.sanguosha.core.gaming;

import me.magicall.game.card.Event;
import me.magicall.game.sanguosha.core.unit.Hero;

/**
 * @author Liang Wenjian
 */
public class CalculateDistanceEvent extends Event {

    private static final long serialVersionUID = -3153485384643909832L;

    private final Hero hero;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public CalculateDistanceEvent(final Sanguosha source, final Hero hero) {
        super(source);
        this.hero = hero;
    }

    public Hero getHero() {
        return hero;
    }
}
