package me.magicall.game.sub.round.skill;

import java.util.List;

import me.magicall.game.sub.round.Round;

/**
 * @author MaGiCalL
 */
public interface Undoable {

	List<? extends Round> undo(int undoRoundCount);

	Round undo();

	/**
	 * @author MaGiCalL
	 */
	public static interface Redoable extends Undoable {
		List<? extends Round> redo(int redoRoundCount);

		Round redo();
	}
}
