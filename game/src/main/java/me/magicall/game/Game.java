package me.magicall.game;

import me.magicall.game.config.GameConfig;
import me.magicall.game.map.GameMap;
import me.magicall.game.map.GamingMap;
import me.magicall.game.player.Player;
import me.magicall.game.player.PlayerRole;
import me.magicall.game.player.Team;
import me.magicall.game.plugin.GamePlugin;

/**
 * 一场游戏
 * 
 * @author MaGiCalL
 */
public interface Game {

	/**
	 * 获得游戏配置
	 * 
	 * @return
	 */
	GameConfig getConfig();

	/**
	 * 获得初始地图
	 * 
	 * @return
	 */
	GameMap getMap();

	/**
	 * 获得当前游戏地图。
	 * 注意需要保证同步。
	 * 
	 * @return
	 */
	GamingMap getGamingMap();

	/**
	 * 游戏是否已经结束。
	 * 
	 * @return
	 */
	boolean isGameOver();

	/**
	 * 游戏开始。
	 */
	void start();

	/**
	 * 添加游戏插件。
	 * 
	 * @param plugin
	 */
	void addGamePlugin(GamePlugin plugin);

	/**
	 * 获取本场游戏中的所有角色。
	 * 
	 * @return
	 */
	PlayerRole[] getPlayerRoles();

	/**
	 * 获取本场游戏中的所有玩家。
	 * 
	 * @return
	 */
	Player[] getPlayers();

	/**
	 * 初始化
	 */
	void init();

	Team[] getTeams();

	Team getWinnerTeam();

	PlayerRole[] getWinners();
}
