package me.magicall.game.skill;

import me.magicall.game.GameException;

public class NoTargetToBeSelectingException extends GameException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8863806132153288862L;

	public NoTargetToBeSelectingException() {
		super();
	}

	public NoTargetToBeSelectingException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public NoTargetToBeSelectingException(final String message) {
		super(message);
	}

	public NoTargetToBeSelectingException(final Throwable cause) {
		super(cause);
	}

}
