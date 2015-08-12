package me.magicall.game.sanguosha.core.gaming;

import me.magicall.game.card.Event;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.List;

/**
 * @author Liang Wenjian
 */
public class GetTargetsEvent extends Event {

    private static final long serialVersionUID = -8762408271750051931L;

    private final List<Hero> targets;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     * @param targets
     */
    public GetTargetsEvent(final Object source, final List<Hero> targets) {
        super(source);
        this.targets = targets;
    }

    public List<Hero> getTargets() {
        return targets;
    }
}
