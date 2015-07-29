package me.magicall.game.skill;

import me.magicall.game.GameException;

public class NoTargetSelectingException extends GameException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3465657685796440604L;

	public NoTargetSelectingException() {
		super();
	}

	public NoTargetSelectingException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public NoTargetSelectingException(final String message) {
		super(message);
	}

	public NoTargetSelectingException(final Throwable cause) {
		super(cause);
	}

}
