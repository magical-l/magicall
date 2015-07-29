package me.magicall.game.sub.round.abs;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import me.magicall.game.data.BaseGameData;
import me.magicall.game.io.GameInput;
import me.magicall.game.io.GameOutput;
import me.magicall.game.map.GameMap;
import me.magicall.game.map.GamingMap;
import me.magicall.game.player.Player;
import me.magicall.game.player.PlayerRole;
import me.magicall.game.plugin.GamePlugin;
import me.magicall.game.sub.round.Round;
import me.magicall.game.sub.round.RoundGameConfig;
import me.magicall.game.sub.round.RoundManager;
import me.magicall.game.sub.round.plugin.RoundPlugin;

public abstract class AbsRoundGame//
<C extends RoundGameConfig, M extends GameMap, GM extends GamingMap, R extends Round> //
		extends RoundGameTemplate {

	protected BaseGameData baseGameData;

	protected RoundManager<R> roundManager;

	protected Collection<GamePlugin> gamePlugins = new LinkedHashSet<>();

	protected Collection<RoundPlugin> roundPlugins = new LinkedList<>();

	protected R lastRound;

	protected R curRound;

	protected GM gamingMap;

	protected PlayerRole[] playerRoles;

	protected GameInput gameInput;
	protected GameOutput gameOutput;

	public AbsRoundGame() {
		super();
	}

	public AbsRoundGame(final RoundGameConfig config, final GM gamingMap, final RoundManager<R> roundManager) {
		super();
		baseGameData = new BaseGameData(config);
		this.roundManager = roundManager;
		this.gamingMap = gamingMap;
		final Player[] players = config.getPlayers();
		final PlayerRole[] playerRoles = new PlayerRole[players.length];
		int index = 0;
		for (final Player player : players) {
			final PlayerRole playerRole = newPlayerRole();
			playerRole.setPlayer(player);
			playerRoles[index++] = playerRole;
		}
	}

	protected abstract PlayerRole newPlayerRole();

	@Override
	@SuppressWarnings("unchecked")
	protected void addRound(final Round round) {
		lastRound = (R) round;
		roundManager.addRound(lastRound);
		setGameOver(checkGameOver());
	}

	@Override
	public List<? extends Round> getRounds() {
		return roundManager.getRounds();
	}

	@Override
	public C getConfig() {
		return baseGameData.getConfig();
	}

	@Override
	public boolean isGameOver() {
		return baseGameData.isGameOver();
	}

	@Override
	public int getRoundsCount() {
		return roundManager.getRoundsCount();
	}

	@Override
	public R getLastRound() {
		return lastRound;
	}

	protected void setGameOver(final boolean gameOver) {
		baseGameData.setGameOver(gameOver);
	}

	protected void setRoundManager(final RoundManager<R> roundManager) {
		this.roundManager = roundManager;
	}

	@Override
	protected Collection<RoundPlugin> getRoundPlugins() {
		return roundPlugins;
	}

	@Override
	public void addGamePlugin(final GamePlugin plugin) {
		gamePlugins.add(plugin);
	}

	@Override
	public void addRoundPlugin(final RoundPlugin plugin) {
		roundPlugins.add(plugin);
	}

	@Override
	public boolean containsRoundPlugin(final RoundPlugin plugin) {
		return roundPlugins.contains(plugin);
	}

	@Override
	public R getCurRound() {
		return curRound;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void setCurRound(final Round round) {
		curRound = (R) round;
	}

	@Override
	protected Collection<GamePlugin> getGamePlugins() {
		return gamePlugins;
	}

	@Override
	public GM getGamingMap() {
		return gamingMap;
	}

	protected void setGamingMap(final GM gamingMap) {
		this.gamingMap = gamingMap;
	}

	protected BaseGameData getBaseGameData() {
		return baseGameData;
	}

	protected void setBaseGameData(final BaseGameData baseGameData) {
		this.baseGameData = baseGameData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public M getMap() {
		return (M) baseGameData.getConfig().getMap();
	}

	protected RoundManager<R> getRoundManager() {
		return roundManager;
	}

	protected void setGamePlugins(final List<GamePlugin> gamePlugins) {
		this.gamePlugins = gamePlugins;
	}

	protected void setRoundPlugins(final List<RoundPlugin> roundPlugins) {
		this.roundPlugins = roundPlugins;
	}

	protected void setLastRound(final R lastRound) {
		this.lastRound = lastRound;
	}

	protected abstract boolean checkGameOver();

	@Override
	public Player[] getPlayers() {
		return getConfig().getPlayers();
	}

	public GameInput getGameInput() {
		return gameInput;
	}

	public void setGameInput(final GameInput gameInput) {
		this.gameInput = gameInput;
	}

	public GameOutput getGameOutput() {
		return gameOutput;
	}

	public void setGameOutput(final GameOutput gameOutput) {
		this.gameOutput = gameOutput;
	}

	@Override
	public PlayerRole[] getPlayerRoles() {
		return playerRoles;
	}

	protected void setPlayerRoles(final PlayerRole... playerRoles) {
		this.playerRoles = playerRoles;
	}

}
