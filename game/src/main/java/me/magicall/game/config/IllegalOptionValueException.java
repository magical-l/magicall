package me.magicall.game.config;

import me.magicall.game.io.OperatingException;

public class IllegalOptionValueException extends OperatingException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6003967301543806084L;

	public IllegalOptionValueException() {
		super();
	}

	public IllegalOptionValueException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public IllegalOptionValueException(final String message) {
		super(message);
	}

	public IllegalOptionValueException(final Throwable cause) {
		super(cause);
	}

}
