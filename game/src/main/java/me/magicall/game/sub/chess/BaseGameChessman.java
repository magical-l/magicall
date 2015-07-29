package me.magicall.game.sub.chess;

import me.magicall.game.Game;
import me.magicall.game.player.PlayerRole;
import me.magicall.game.unit.GamingUnit;

public class BaseGameChessman extends BaseChessman implements GamingUnit {

	protected final Game game;
	protected PlayerRole owner;

	public BaseGameChessman(final String name, final Game game, final PlayerRole owner) {
		super(name);
		this.game = game;
		this.owner = owner;
	}

	@Override
	public PlayerRole getOwner() {
		return owner;
	}

	@Override
	public Game getGame() {
		return game;
	}

	@Override
	public void setOwner(final PlayerRole owner) {
		this.owner = owner;
	}

}
