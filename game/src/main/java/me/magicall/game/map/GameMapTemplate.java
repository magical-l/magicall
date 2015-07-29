package me.magicall.game.map;

import me.magicall.coll.CollFactory;
import me.magicall.coll.CollFactory.L;

public abstract class GameMapTemplate implements GameMap {

	public GameMapTemplate() {
		super();
	}

	@Override
	public void traverse(final MapTraverseHandler... traverseHandlers) {
		traverse(L.asList(traverseHandlers));
	}

	@Override
	public void traverse(final MapTraverseHandler traverseHandler) {
		traverse(L.asList(traverseHandler));
	}
}