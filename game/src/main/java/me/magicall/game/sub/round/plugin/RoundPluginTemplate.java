package me.magicall.game.sub.round.plugin;

import me.magicall.game.Game;
import me.magicall.game.sub.round.Round;

public class RoundPluginTemplate<G extends Game, R extends Round> implements RoundPlugin {

	@SuppressWarnings("unchecked")
	@Override
	public final void addingRound(final Game game, final Round round) {
		addingRoundInternal((G) game, (R) round);
	}

	protected void addingRoundInternal(final G sudoku, final R round) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public final void endedRound(final Game game, final Round round) {
		endedRoundInternal((G) game, (R) round);
	}

	protected void endedRoundInternal(final G sudoku, final R round) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public final void endingRound(final Game game, final Round round) {
		endingRoundInternal((G) game, (R) round);
	}

	protected void endingRoundInternal(final G sudoku, final R round) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public final void startingRound(final Game game, final Round round) {
		startingRoundInternal((G) game, (R) round);
	}

	protected void startingRoundInternal(final G sudoku, final R round) {
	}
}
