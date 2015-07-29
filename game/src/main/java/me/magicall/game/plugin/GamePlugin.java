package me.magicall.game.plugin;

import me.magicall.game.Game;

/**
 * 游戏插件。是一种监听器，监听游戏即将开始、游戏即将结束时、游戏结束后。
 * 
 * @author MaGiCalL
 */
public interface GamePlugin {

	/**
	 * 游戏即将开始时执行的操作。
	 * 
	 * @param game
	 */
	void gameStarting(Game game);

	/**
	 * 游戏即将结束时的操作。
	 * 
	 * @param game
	 */
	void gameEnding(Game game);

	/**
	 * 游戏结束后的操作。
	 * 
	 * @param game
	 */
	void gameEnded(Game game);
}
