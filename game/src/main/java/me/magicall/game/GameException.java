package me.magicall.game;

public class GameException extends Exception {
	private static final long serialVersionUID = 8151255331280076580L;

	public GameException() {
		super();
	}

	public GameException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public GameException(final String message) {
		super(message);
	}

	public GameException(final Throwable cause) {
		super(cause);
	}

}
