package me.magicall.game.sub.chess;

import java.util.Collection;

import me.magicall.game.map.Coordinate;
import me.magicall.game.map.GameMapTemplate;

public abstract class ChessBoardTemplate extends GameMapTemplate implements ChessBoard {
	@Override
	public int getCoordinateCount() {
		return getColumnsCount() * getRowsCount();
	}

	@Override
	public void traverse(final Collection<? extends MapTraverseHandler> traverseHandlers) {
		final int rows = getColumnsCount();
		final int columns = getRowsCount();
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < columns; ++j) {
				final Coordinate p = getCoordinate(i, j);
				for (final MapTraverseHandler h : traverseHandlers) {
					if (!h.handle(p)) {
						return;
					}
				}
			}//for j
		}//for i
	}

	@Override
	public int getDimCount() {
		return 2;
	}

	@Override
	public int getHeight() {
		return getRowsCount();
	}

	@Override
	public int getWidth() {
		return getColumnsCount();
	}
}
