package me.magicall.game.sanguosha.core.gaming.position;

import me.magicall.game.sanguosha.core.gaming.option.Selection;

import java.util.List;

/**
 * @author Liang Wenjian
 */
public class PositionsSelection implements Selection {

    private final List<Integer> positions;

    public PositionsSelection(final List<Integer> positions) {
        this.positions = positions;
    }

    public List<Integer> getPositions() {
        return positions;
    }
}
