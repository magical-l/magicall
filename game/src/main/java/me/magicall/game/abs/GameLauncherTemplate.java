package me.magicall.game.abs;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.magicall.game.Game;
import me.magicall.game.GameLauncher;
import me.magicall.game.config.GameConfig;
import me.magicall.game.config.GameOption;
import me.magicall.game.config.OptionItem;
import me.magicall.game.io.GameInput;
import me.magicall.game.io.GameOutput;
import me.magicall.game.io.InputHandler;
import me.magicall.game.io.OperatingException;
import me.magicall.game.player.Player;
import me.magicall.game.plugin.GameLauncherPlugin;
import me.magicall.game.plugin.GamePlugin;
import me.magicall.game.util.GameUtil;
import me.magicall.util.ClassUtil;
import me.magicall.util.kit.Kits;

/**
 * 游戏启动器的抽象模板实现。
 * 
 * @author MaGiCalL
 */
public abstract class GameLauncherTemplate implements GameLauncher {

	/*
	 * (non-Javadoc)
	 * @see me.magicall.game.abs.GameLauncher#launch()
	 */
	@Override
	public Game launch(final Player mainPlayer) {
		//主要用户进行各种选项
		final GameOption gameOption = options(mainPlayer);

		//把用户的选项转化成游戏配置
		final GameConfig gameConfig = optionToConfig(gameOption);
		//创建一个游戏
		final Game game = newGame(gameConfig);

		//加载游戏插件
		loadGamePlugins(game);

		//启动各个游戏启动器插件的“运行前程序”
		launcherPluginsBeforeGameStart(game);

		//准备启动游戏
		loading(game);

		//启动游戏
		game.start();

		//游戏结束，启动各个游戏启动器插件的“结束后程序”
		launcherPluginsAfterGame(game);

		return game;
	}

	/**
	 * 主机玩家对各游戏参数进行选择
	 * 
	 * @return
	 */
	protected GameOption options(final Player mainPlayer) {
		final GameOutput gameOutput = mainPlayer.getGameOutput();
		final GameInput gameInput = mainPlayer.getGameInput();
		//创建一个游戏选项供用户进行参数选择
		final GameOption gameOption = newGameOption();
		gameOption.setMainPlayer(mainPlayer);

		//使用游戏启动器插件对选项对象进行操作。有些选项插件可以添加一些可选参数
		final List<? extends GameLauncherPlugin> gameLauncherPlugins = getGameLauncherPlugins();
		for (final GameLauncherPlugin plugin : gameLauncherPlugins) {
			plugin.beforeConfigGameOption(gameOption);
		}

		//获得游戏选项的每一个参数选项。
		final List<OptionItem> optionItems = gameOption.getOptionItems();

		for (final OptionItem optionItem : optionItems) {
			final String tip = optionItemTip(optionItem);
			while (true) {
				//输出简单提示
				gameOutput.output(this, tip);
				//输出可选项或者补充提示
				optionItem.showAvailableValues(gameOutput);
				try {
					gameInput.requestInput(nextInput -> optionItem.setValue(gameOption, nextInput));
					break;
				} catch (final OperatingException e) {
					GameUtil.showException(GameLauncherTemplate.this, gameOutput, e);
				}
			}//while
		}//for

		return gameOption;
	}

	/**
	 * @param game
	 */
	protected void loading(final Game game) {
		final Player[] players = game.getConfig().getPlayers();
		for (final Player player : players) {
			player.getGameOutput().output(this, "loading...");
		}

		game.init();
	}

	/**
	 * 游戏启动器的插件。
	 * 
	 * @return
	 */
	protected List<? extends GameLauncherPlugin> getGameLauncherPlugins() {
		return Kits.LIST.emptyValue();
	}

	/**
	 * 启动各个游戏启动器插件的“运行前程序”
	 * 
	 * @param game
	 */
	protected void launcherPluginsBeforeGameStart(final Game game) {
		final List<? extends GameLauncherPlugin> gameLauncherPlugins = getGameLauncherPlugins();
		for (final GameLauncherPlugin plugin : gameLauncherPlugins) {
			plugin.beforeGameStart(game);
		}
	}

	/**
	 * 游戏结束，启动各个游戏启动器插件的“结束后程序”
	 * 
	 * @param game
	 */
	protected void launcherPluginsAfterGame(final Game game) {
		final List<? extends GameLauncherPlugin> gameLauncherPlugins = getGameLauncherPlugins();
		for (final GameLauncherPlugin plugin : gameLauncherPlugins) {
			plugin.afterGame(game);
		}
	}

	/**
	 * 安装游戏插件
	 * 
	 * @param game
	 */
	protected void loadGamePlugins(final Game game) {
		final List<? extends GamePlugin> gamePlugins = getGamePlugins();
		for (final GamePlugin p : gamePlugins) {
			game.addGamePlugin(p);
		}
	}

	/**
	 * 创建一个游戏选项对象供用户选择参数
	 * 
	 * @return
	 */
	protected abstract GameOption newGameOption();

	/**
	 * 把用户的选项转化成游戏配置
	 * 
	 * @param gameOption
	 * @return
	 */
	protected GameConfig optionToConfig(final GameOption gameOption) {
		return gameOption;
	}

	/**
	 * 创建一个游戏
	 * 
	 * @param config
	 * @return
	 */
	protected abstract Game newGame(GameConfig config);

	/**
	 * 获取当前获得焦点的参数的提示文案，用来展示给用户
	 * 
	 * @param optionItem
	 * @return
	 */
	protected String optionItemTip(final OptionItem optionItem) {
		return optionItem.getName();
	}

	/**
	 * 获取游戏插件列表
	 * 
	 * @return
	 */
	protected List<? extends GamePlugin> getGamePlugins() {
		final Package package1 = getClass().getPackage();
		final String name = package1.getName() + ".plugins";//默认路径:Launcher类所在包的plugins子包
		final Package package2 = Package.getPackage(name);
		if (package2 == null) {
			return Kits.LIST.emptyValue();
		}
		final Collection<Class<?>> pluginsClasses = ClassUtil.getClasses(package2);
		final List<GamePlugin> list = new ArrayList<>(pluginsClasses.size());
		for (final Class<?> c : pluginsClasses) {
			if (GamePlugin.class.isAssignableFrom(c)) {
				final int modifiers = c.getModifiers();
				//一个GamePlugin必须是实现了GamePlugin接口的、public的、非abstract的、非内部类的。
				if (Modifier.isPublic(modifiers)//
						&& !Modifier.isAbstract(modifiers)//
						&& !c.isLocalClass()//
						&& !c.isMemberClass()) {
					final GamePlugin plugin = (GamePlugin) ClassUtil.newInstance(c);
					list.add(plugin);
				}
			}
		}
		return list;
	}
}
