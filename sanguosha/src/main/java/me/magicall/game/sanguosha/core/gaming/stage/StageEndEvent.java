package me.magicall.game.sanguosha.core.gaming.stage;

import me.magicall.game.card.Event;

/**
 * @author Liang Wenjian
 */
public class StageEndEvent extends Event {

    private static final long serialVersionUID = -61684842268919131L;

    public StageEndEvent(final Stage source) {
        super(source);
    }

    @Override
    public Stage getSource() {
        return (Stage) super.getSource();
    }
}
