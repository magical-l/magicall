/**
 * 
 */
package me.magicall.game.sub.chess;

import me.magicall.game.map.Coordinate;
import me.magicall.game.map.GameMap.MapTraverseHandler;

public abstract class ChessBoardTraverseHandler implements MapTraverseHandler {
	@Override
	public boolean handle(final Coordinate coordinate) {
		return handle((Position) coordinate);
	}

	protected abstract boolean handle(Position position);
}