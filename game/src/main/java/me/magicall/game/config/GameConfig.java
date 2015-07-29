package me.magicall.game.config;

import me.magicall.game.map.GameMap;
import me.magicall.game.player.Player;

/**
 * 一场游戏的配置
 * 
 * @author MaGiCalL
 */
public interface GameConfig {

	/**
	 * 获取本场主要玩家。在联网游戏中是主机的玩家。
	 * 
	 * @return
	 */
	Player getMainPlayer();

	/**
	 * 获取本场游戏的所有玩家。
	 * 
	 * @return
	 */
	Player[] getPlayers();

	/**
	 * 获取本场游戏的玩家数量。
	 * 
	 * @return
	 */
	int getPlayersCount();

	/**
	 * 获取初始化的地图。
	 * 
	 * @return
	 */
	GameMap getMap();

}