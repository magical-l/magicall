package me.magicall.game.abs;

import me.magicall.game.config.GameConfig;

public abstract class GameConfigTemplate implements GameConfig {

	@Override
	public int getPlayersCount() {
		return getPlayers().length;
	}

}
