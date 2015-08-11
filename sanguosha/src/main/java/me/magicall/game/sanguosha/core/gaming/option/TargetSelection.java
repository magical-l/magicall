package me.magicall.game.sanguosha.core.gaming.option;

import java.util.List;

/**
 * @author Liang Wenjian
 */
public class TargetSelection {

    private List<Integer> positions;

    public TargetSelection() {
        super();
    }

    public TargetSelection(final List<Integer> positions) {
        this.positions = positions;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public void setPositions(final List<Integer> positions) {
        this.positions = positions;
    }
}
