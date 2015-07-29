package me.magicall.game;

import me.magicall.game.io.OutputSource;
import me.magicall.game.player.Player;

/**
 * 游戏启动器。builder模式。
 * 
 * @author MaGiCalL
 */
public interface GameLauncher extends OutputSource {

	/**
	 * 启动一个游戏
	 */
	Game launch(Player mainPlayer);

}