package me.magicall.game.sub.chess;

import me.magicall.game.map.Coordinate;
import me.magicall.game.util.GameUtil;

public class Position implements Coordinate, Comparable<Position> {

	private final int row;
	private final int column;

	public Position(final int row, final int column) {
		super();
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public String toString() {
		return "(" + row + ',' + column + ')';
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Position other = (Position) obj;
		if (column != other.column) {
			return false;
		}
		if (row != other.row) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(final Position o) {
		return GameUtil.COORDINATE_COMPARATOR.compare(this, o);
	}

	@Override
	public int[] getCoordinateNums() {
		return new int[] { getRow(), getColumn() };
	}

	public static Position[][] buildPositions(final int rows, final int columns) {
		final Position[][] rt = new Position[rows][columns];
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < columns; ++j) {
				rt[i][j] = new Position(i, j);
			}
		}
		return rt;
	}

	public static void main(final String... args) {
		final Position[][] positions = buildPositions(3, 4);
		for (final Position[] ps : positions) {
			for (final Position p : ps) {
				System.out.println(p);
			}
		}
	}
}
