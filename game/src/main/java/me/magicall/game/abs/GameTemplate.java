package me.magicall.game.abs;

import java.util.Collection;

import me.magicall.game.Game;
import me.magicall.game.plugin.GamePlugin;
import me.magicall.util.kit.Kits;

/**
 * 游戏模板实现
 * 
 * @author MaGiCalL
 */
public abstract class GameTemplate implements Game {

	@Override
	public void start() {
		//游戏开始时触发插件
		pluginGameStarting(this);
		//游戏运行
		while (!isGameOver()) {
			run();
		}
		//游戏结束时触发插件
		pluginGameEnding(this);
		//游戏结束
		end();
		//游戏结束后出发插件
		pluginGameEnded(this);
	}

	protected void pluginGameEnded(final Game game) {
		final Collection<GamePlugin> gamePlugins = getGamePlugins();
		for (final GamePlugin plugin : gamePlugins) {
			plugin.gameEnded(game);
		}
	}

	protected void pluginGameEnding(final Game game) {
		final Collection<GamePlugin> gamePlugins = getGamePlugins();
		for (final GamePlugin plugin : gamePlugins) {
			plugin.gameEnding(game);
		}
	}

	protected void pluginGameStarting(final Game game) {
		final Collection<GamePlugin> gamePlugins = getGamePlugins();
		for (final GamePlugin plugin : gamePlugins) {
			plugin.gameStarting(game);
		}
	}

	protected Collection<GamePlugin> getGamePlugins() {
		return Kits.LIST.emptyValue();
	}

	protected abstract void run();

	protected void end() {

	}

	@Override
	public void init() {

	}

}
