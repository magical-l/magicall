package me.magicall.game.io;

import me.magicall.game.GameException;

public class OperatingException extends GameException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6385667895115676090L;

	public OperatingException() {
		super();
	}

	public OperatingException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public OperatingException(final String message) {
		super(message);
	}

	public OperatingException(final Throwable cause) {
		super(cause);
	}

}
