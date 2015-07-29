package me.magicall.game.skill;

import java.util.Collection;

import me.magicall.game.Game;
import me.magicall.game.io.InputHandler;
import me.magicall.game.io.OperatingException;
import me.magicall.game.player.Player;
import me.magicall.game.sub.chess.Position;
import me.magicall.game.sub.chess.PositionSelector;

public abstract class SelectingTargetsSkillTemplate extends SkillTemplate implements TargetedSkill {

	protected SelectingTargetsSkillTemplate() {
	}

	@Override
	protected Collection<?> select(final Game game, final Player player) throws NoTargetSelectingException {
		final String tip = selectingTargetTip(game, player);
		player.getGameOutput().output(this, tip);

		class A implements InputHandler {
			Collection<?> units;

			@Override
			public void handle(final Object nextInput) throws OperatingException {
				final TargetSelector targetSelector = getTargetSelector();
				try {
					units = targetSelector.select(game, () -> nextInput);
				} catch (final NoTargetToBeSelectingException e) {
					throw new OperatingException(e);
				}
			}
		}
		final A a = new A();
		try {
			player.getGameInput().requestInput(a);
		} catch (final OperatingException e) {
			throw new NoTargetSelectingException(e);
		}
		return a.units;
	}

	protected String selectingTargetTip(final Game game, final Player player) {
		return "选择目标：";
	}

	@Override
	public TargetSelector getTargetSelector() {
		return new PositionSelector();
	}

	@Override
	protected final void action(final Game game, final Player player, final Collection<?> targets) throws OperatingException {
		action(game, player, (Position) targets.iterator().next());
	}

	protected abstract void action(Game game, Player player, Position position) throws OperatingException;
}
