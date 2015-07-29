package me.magicall.game.sub.round.plugin;

import me.magicall.game.Game;
import me.magicall.game.sub.round.Round;

public interface RoundPlugin {

	void startingRound(Game game, Round round);

	void addingRound(Game game, Round round);

	void endingRound(Game game, Round round);

	void endedRound(Game game, Round round);
}
