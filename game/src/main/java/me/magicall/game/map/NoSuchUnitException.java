package me.magicall.game.map;

import me.magicall.game.GameException;

public class NoSuchUnitException extends GameException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9222074064326730608L;

	public NoSuchUnitException() {
		super();
	}

	public NoSuchUnitException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public NoSuchUnitException(final String message) {
		super(message);
	}

	public NoSuchUnitException(final Throwable cause) {
		super(cause);
	}

}
