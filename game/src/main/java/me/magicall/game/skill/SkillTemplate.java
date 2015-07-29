package me.magicall.game.skill;

import java.util.Collection;

import me.magicall.game.Game;
import me.magicall.game.io.OperatingException;
import me.magicall.game.player.Player;

public abstract class SkillTemplate implements Skill {

	@Override
	public char getHotKey() {
		return getName().charAt(0);
	}

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public String getTip() {
		return getName();
	}

	protected abstract void action(Game game, Player player, Collection<?> targets) throws OperatingException;

	protected abstract Collection<?> select(final Game game, Player player) throws NoTargetSelectingException;

	@Override
	public void action(final Game game, final Player player) throws OperatingException, NoTargetSelectingException {
		final Collection<?> targets = select(game, player);
		action(game, player, targets);
	}
}
