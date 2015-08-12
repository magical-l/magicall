package me.magicall.game.sanguosha.core.gaming.stage;

import me.magicall.game.card.Event;

/**
 * @author Liang Wenjian
 */
public class CalculateDiscardCountEvent extends Event {

    private static final long serialVersionUID = -6947752003272205768L;

    private int availableRemainCount;

    public CalculateDiscardCountEvent(final DiscardStage source, final int availableRemainCount) {
        super(source);
        this.availableRemainCount = availableRemainCount;
    }

    public int getAvailableRemainCount() {
        return availableRemainCount;
    }

    public void setAvailableRemainCount(final int availableRemainCount) {
        this.availableRemainCount = availableRemainCount;
    }
}
