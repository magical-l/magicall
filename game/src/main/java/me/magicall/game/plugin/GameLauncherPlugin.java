package me.magicall.game.plugin;

import me.magicall.game.Game;
import me.magicall.game.config.GameOption;

/**
 * 游戏启动器的插件。是一种监听器，监听游戏启动器启动游戏前后、主机玩家进行游戏选项。
 * 
 * @author MaGiCalL
 */
public interface GameLauncherPlugin {

	void beforeGameStart(Game game);

	void afterGame(Game game);

	void beforeConfigGameOption(GameOption gameOption);
}
