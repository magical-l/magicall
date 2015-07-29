package me.magicall.game.player;

import me.magicall.game.Game;

public class CommonTeam extends TeamTemplate {

	private Game game;
	private String name;
	private final int order;
	private final PlayerRole[] playerRoles;

	public CommonTeam(final String name, final int order, final PlayerRole... players) {
		super();
		this.name = name;
		this.order = order;
		playerRoles = players;
	}

	@Override
	public PlayerRole[] getPlayerRoles() {
		return playerRoles;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getOrder() {
		return order;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public Game getGame() {
		return game;
	}

	public void setGame(final Game game) {
		this.game = game;
	}

}
