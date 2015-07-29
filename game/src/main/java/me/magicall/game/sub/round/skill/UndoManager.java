package me.magicall.game.sub.round.skill;

import java.util.ArrayList;
import java.util.List;

import me.magicall.coll.CollectionFactory;
import me.magicall.game.sub.round.Round;
import me.magicall.game.sub.round.RoundManager;

public class UndoManager<R extends Round> extends RoundManager<R> implements Undoable {

	protected int maxUndoRoundCount;

	public UndoManager(final CollectionFactory collectionFactory, final int maxUndoRoundCount) {
		super(collectionFactory);
		this.maxUndoRoundCount = maxUndoRoundCount;
	}

	@Override
	public R undo() {
		if (maxUndoRoundCount == 0) {
			throw roundCountTooLarge(1);
		}
		return popLastRound();
	}

	private RuntimeException roundCountTooLarge(final int roundCount) {
		return new IllegalArgumentException("max undo step available is:" + maxUndoRoundCount + ", " + roundCount + " is too more");
	}

	@Override
	public List<R> undo(int roundCount) {
		if (roundCount > maxUndoRoundCount) {
			throw roundCountTooLarge(roundCount);
		}
		roundCount = Math.min(roundCount, rounds.size());
		final List<R> list = new ArrayList<>(roundCount);
		for (int i = 0; i < roundCount; ++i) {
			final R round = popLastRound();
			if (round == null) {//恢复?
				return list;
			}
			list.add(round);
		}
		return list;
	}

	public int getMaxUndoRoundCount() {
		return maxUndoRoundCount;
	}

	public void setMaxUndoRoundCount(final int maxUndoRoundCount) {
		this.maxUndoRoundCount = maxUndoRoundCount;
	}
}
