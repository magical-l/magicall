package me.magicall.game.sanguosha.core.gaming.position;

import me.magicall.game.card.Coordinate;

import java.util.Objects;

/**
 * 位置。
 *
 * @author Liang Wenjian
 */
public class Position implements Coordinate<Position, Integer>, Comparable<Position> {

    private final int position;

    public Position(final int position) {
        super();
        this.position = position;
    }

    @Override
    public Integer distance(final Position other) {
        return Math.abs(position - other.position);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }
        final Position position1 = (Position) o;
        return Objects.equals(position, position1.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public String toString() {
        return "Position:" + position;
    }

    @Override
    public int compareTo(final Position o) {
        return Integer.compare(position, o.position);
    }
}
