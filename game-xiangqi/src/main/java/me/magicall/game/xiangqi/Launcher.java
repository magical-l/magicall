package me.magicall.game.xiangqi;

import java.util.List;

import me.magicall.game.Game;
import me.magicall.game.abs.GameLauncherTemplate;
import me.magicall.game.config.GameConfig;
import me.magicall.game.config.GameOption;
import me.magicall.game.config.OptionItem;
import me.magicall.game.player.CommonPlayer;
import me.magicall.game.player.Player;
import me.magicall.game.xiangqi.config.Config;
import me.magicall.game.xiangqi.config.Option;

public class Launcher extends GameLauncherTemplate {

	@Override
	protected Game newGame(final GameConfig config) {
		return new Xiangqi((Config) config);
	}

	@Override
	protected GameOption newGameOption() {
		return new ConfigImpl();
	}

	private static class ConfigImpl implements Config, Option {

		private final CommonPlayer[] players = new CommonPlayer[Util.MAX_PLAYERS_COUNT];
		private Board map;

		@Override
		public CommonPlayer[] getPlayers() {
			return players;
		}

		@Override
		public CommonPlayer getMainPlayer() {
			return players[0];
		}

		@Override
		public Board getMap() {
			return map;
		}

		@Override
		public int getPlayersCount() {
			return Util.MAX_PLAYERS_COUNT;
		}

		@Override
		public void addOptionItem(final OptionItem optionItem) {
			// TODO Auto-generated method stub

		}

		@Override
		public List<OptionItem> getOptionItems() {
			// TODO Auto-generated method stub
			return null;
		}

		public void setOptionValue(final OptionItem optionItem, final Object value) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setMainPlayer(final Player player) {
			// TODO Auto-generated method stub

		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}
