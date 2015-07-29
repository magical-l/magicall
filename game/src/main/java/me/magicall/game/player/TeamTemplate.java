package me.magicall.game.player;

public abstract class TeamTemplate implements Team {

	@Override
	public int compareTo(final Team o) {
		return getOrder() - o.getOrder();
	}

}
