/**
 * 
 */
package me.magicall.game.sub.chess;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;


public class PositionGroup {

	private final SortedSet<Position> positions;

	public PositionGroup() {
		positions = new TreeSet<>();
	}

	public Collection<Position> getPositions() {
		return positions;
	}

	public void addPosition(final Position position) {
		positions.add(position);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (final Position c : positions) {
			sb.append(c).append(' ');
		}
		return sb.toString();
	}
}