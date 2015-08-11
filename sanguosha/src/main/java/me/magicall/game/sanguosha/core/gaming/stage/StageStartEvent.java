package me.magicall.game.sanguosha.core.gaming.stage;

import me.magicall.game.card.Event;

/**
 * @author Liang Wenjian
 */
public class StageStartEvent extends Event {

    private static final long serialVersionUID = 4877505663494214017L;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public StageStartEvent(final Stage source) {
        super(source);
    }

    @Override
    public Stage getSource() {
        return (Stage) super.getSource();
    }
}
