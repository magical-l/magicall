package me.magicall.game.sub.round.plugin;

import java.util.List;

import me.magicall.coll.CommonCollectionFactory;
import me.magicall.game.Game;
import me.magicall.game.io.OperatingException;
import me.magicall.game.player.PlayerRole;
import me.magicall.game.sub.round.Round;
import me.magicall.game.sub.round.skill.UndoManager;
import me.magicall.game.sub.round.skill.UndoRedoManager;

public abstract class AbsRedoPlugin<G extends Game, R extends Round> extends AbsUndoPlugin<G, R> {

	private class Redo extends Undo {

		@Override
		public String getTip() {
			return "重做撤销";
		}

		@Override
		protected void checkRoundsCount() throws OperatingException {
			final UndoRedoManager<R> undoRedoManager = (UndoRedoManager<R>) getUndoManager();
			if (undoRedoManager.getRedosCount() == 0) {
				throw new OperatingException("当前没有可" + getTip() + "的回合");
			}
		}

		@Override
		protected void restoreMap(final G sudoku, final int stepCount) {
			final UndoRedoManager<R> undoRedoManager = (UndoRedoManager<R>) getUndoManager();
			final List<R> canceledRounds = undoRedoManager.redo(stepCount);
			undoRounds(sudoku, canceledRounds, false);
		}
	}

	@Override
	protected UndoManager<R> newUndoManager(final int maxUndoRoundCount) {
		return new UndoRedoManager<>(CommonCollectionFactory.INSTANCE, maxUndoRoundCount);
	}

	@Override
	protected void addSkill(final Game game) {
		super.addSkill(game);
		final PlayerRole[] playerRoles = game.getPlayerRoles();
		for (final PlayerRole playerRole : playerRoles) {
			playerRole.addSkill(new Redo());//玩家得到一个技能：Redo
		}
	}
}
