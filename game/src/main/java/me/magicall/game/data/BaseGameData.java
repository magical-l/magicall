package me.magicall.game.data;

import me.magicall.game.config.GameConfig;

public class BaseGameData {
	private volatile boolean gameOver;

	private final GameConfig config;

	public BaseGameData(final GameConfig config) {
		this.config = config;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	@SuppressWarnings("unchecked")
	public <C extends GameConfig> C getConfig() {
		return (C) config;
	}

	public void setGameOver(final boolean gameOver) {
		this.gameOver = gameOver;
	}

}
