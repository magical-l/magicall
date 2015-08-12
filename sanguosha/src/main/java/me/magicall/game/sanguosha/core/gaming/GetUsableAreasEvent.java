package me.magicall.game.sanguosha.core.gaming;

import me.magicall.game.card.Event;
import me.magicall.game.sanguosha.core.area.Area;

import java.util.List;

/**
 * @author Liang Wenjian
 */
public class GetUsableAreasEvent extends Event {

    private static final long serialVersionUID = -883927104149416092L;

    private final List<Area> areas;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     * @param areas
     */
    public GetUsableAreasEvent(final Object source, final List<Area> areas) {
        super(source);
        this.areas = areas;
    }

    public List<Area> getAreas() {
        return areas;
    }
}
