package me.magicall.game.sub.chess;


public abstract class ChessmanTemplate implements Chessman {

	@Override
	public char getShowChar() {
		return getShowName().charAt(0);
	}

	@Override
	public String getShowName() {
		return getName();
	}

	@Override
	public String toString() {
		return getShowName();
	}
}
