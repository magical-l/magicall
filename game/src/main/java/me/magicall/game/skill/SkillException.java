package me.magicall.game.skill;

import me.magicall.game.GameException;

public class SkillException extends GameException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8055095241895840664L;

	public SkillException() {
		super();
	}

	public SkillException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public SkillException(final String message) {
		super(message);
	}

	public SkillException(final Throwable cause) {
		super(cause);
	}

}
