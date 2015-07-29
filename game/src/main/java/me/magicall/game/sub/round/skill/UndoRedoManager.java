package me.magicall.game.sub.round.skill;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import me.magicall.coll.CollectionFactory;
import me.magicall.game.sub.round.Round;
import me.magicall.game.sub.round.skill.Undoable.Redoable;
import me.magicall.util.kit.Kits;

public class UndoRedoManager<R extends Round> extends UndoManager<R> implements Redoable {

	private Deque<R> redos;

	public UndoRedoManager(final CollectionFactory collectionFactory, final int maxUndoRoundCount) {
		super(collectionFactory, maxUndoRoundCount);
	}

	private R redoInternal() {
		final R nextRound = redos.pollLast();
		rounds.add(nextRound);
		return nextRound;
	}

	@Override
	public R redo() {
		if (!Kits.COLL.isEmpty(redos)) {
			return redoInternal();
		}
		return null;
	}

	@Override
	public List<R> redo(final int roundCount) {
		if (!Kits.COLL.isEmpty(redos)) {
			final List<R> list = new ArrayList<>(roundCount);
			for (int i = 0; i < roundCount; ++i) {
				final R nextRound = redoInternal();
				list.add(nextRound);
			}
			return list;
		}
		return Kits.LIST.emptyValue();
	}

	@Override
	public R popLastRound() {
		final R round = super.popLastRound();
		if (round == null) {
			return null;
		}
		if (redos == null) {
			redos = new LinkedList<>();
		}
		redos.add(round);
		return round;
	}

	public Deque<R> getRedos() {
		return redos;
	}

	public int getRedosCount() {
		return redos.size();
	}

	@Override
	public boolean addRound(final R round) {
		if (!super.addRound(round)) {
			return false;
		}
//		//drop the redos
//		if (!Kits.COLL.isEmpty(redos)) {
//			redos = null;
//		}
		return true;
	}

}
