package me.magicall.game.sub.round.plugin;

import java.util.Collection;
import java.util.List;

import me.magicall.coll.CommonCollectionFactory;
import me.magicall.game.Game;
import me.magicall.game.config.GameOption;
import me.magicall.game.config.IllegalOptionValueException;
import me.magicall.game.config.OptionItem;
import me.magicall.game.io.GameInput;
import me.magicall.game.io.GameOutput;
import me.magicall.game.io.InputHandler;
import me.magicall.game.io.OperatingException;
import me.magicall.game.player.Player;
import me.magicall.game.player.PlayerRole;
import me.magicall.game.plugin.GameLauncherPlugin;
import me.magicall.game.skill.SkillTemplate;
import me.magicall.game.sub.round.Round;
import me.magicall.game.sub.round.RoundGame;
import me.magicall.game.sub.round.skill.UndoManager;
import me.magicall.game.util.GameUtil;
import me.magicall.util.kit.Kits;

public abstract class AbsUndoPlugin<G extends Game, R extends Round> implements GameLauncherPlugin {

	protected UndoManager<R> undoManager;

	protected class Undo extends SkillTemplate {

		@Override
		public String getTip() {
			return "撤销";
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void action(final Game game, final Player player, final Collection<?> targets) throws OperatingException {
			checkRoundsCount();
			class A implements InputHandler {
				int c;

				@Override
				public void handle(final Object nextInput) throws OperatingException {
					final String string = (String) nextInput;

					final Integer integer = Kits.INT.fromString(string);
					if (integer == null) {
						throw new OperatingException("输入的不是数字。请输入" + getTip() + "回合数（数字）：");
					}
					c = integer;
					final int maxUndoCount = getMaxUndoCount();
					if (c > maxUndoCount) {
						throw new OperatingException("最多" + getTip() + maxUndoCount + " 回合");
					}
				}
			}

			final GameInput gameInput = player.getGameInput();
			final GameOutput gameOutput = player.getGameOutput();
			gameOutput.output(this, "输入" + getTip() + "回合数（数字）：");
			final A a = new A();
			while (true) {
				try {
					gameInput.requestInput(a);
					break;
				} catch (final OperatingException e) {
					GameUtil.showException(Undo.this, gameOutput, e);
				}
			}

			restoreMap((G) game, a.c);//撤销
		}

		protected void checkRoundsCount() throws OperatingException {
			if (undoManager.getRoundsCount() == 0) {
				throw new OperatingException("当前没有可" + getTip() + "的回合");
			}
		}

		protected void restoreMap(final G game, final int stepCount) {
			final List<R> canceledRounds = undoManager.undo(stepCount);
			undoRounds(game, canceledRounds, true);
		}

		protected void undoRounds(final G game, final List<R> list, final boolean useOld) {
			for (final R round : list) {
				undoRound(game, round, useOld);
			}
		}

		@Override
		protected Collection<?> select(final Game game, final Player player) {
			return Kits.LIST.emptyValue();
		}
	}

	protected abstract void undoRound(final G game, final R round, final boolean useOld);

	@Override
	public void afterGame(final Game game) {
	}

	@Override
	public void beforeGameStart(final Game game) {
		if (undoManager != null) {
			((RoundGame) game).addRoundPlugin(new RoundPluginTemplate<Game, R>() {//每回合记录操作以便回滚
						@Override
						protected void endedRoundInternal(final Game game, final R round) {
							undoManager.addRound(round);
						}
					});
			addSkill(game);
		}
	}

	/**
	 * @param game
	 */
	protected void addSkill(final Game game) {
		final PlayerRole[] playerRoles = game.getPlayerRoles();
		for (final PlayerRole playerRole : playerRoles) {
			playerRole.addSkill(newUndoSkill());//玩家获得一个技能:Undo
		}
	}

	protected Undo newUndoSkill() {
		return new Undo();
	}

	protected UndoManager<R> newUndoManager(final int maxUndoRoundCount) {
		return new UndoManager<>(CommonCollectionFactory.INSTANCE, maxUndoRoundCount);
	}

	@Override
	public void beforeConfigGameOption(final GameOption gameOption) {
		gameOption.addOptionItem(new OptionItem() {//增加一个选项：最大撤销步数。

					@Override
					public void setValue(final GameOption gameOption, final Object inputObject) throws IllegalOptionValueException {
						final String string = (String) inputObject;
						final Integer i;
						if (Kits.STR.isEmpty(string)) {
							i = 0;
						} else {
							i = Kits.INT.fromString(string);
							if (i == null) {
								throw new IllegalOptionValueException("输入的数字不对。" + minAvailable() + '~' + maxAvailable() + ")，默认为" + minAvailable());
							}
							if (i < minAvailable() || i > maxAvailable()) {
								throw new IllegalOptionValueException("输入的数字不对。" + minAvailable() + '~' + maxAvailable() + ")，默认为" + minAvailable());
							}
						}
						if (!Kits.INT.isEmpty(i)) {
							undoManager = newUndoManager(i);
						}
					}

					@Override
					public void showAvailableValues(final GameOutput gameOutput) {
						gameOutput.output(this, "输入最大撤销步数(" + minAvailable() + '~' + maxAvailable() + ")，默认为" + minAvailable());
					}

					@Override
					public String getName() {
						return "设置撤销步数";
					}
				});
	}

	protected int getMaxUndoCount() {
		return undoManager.getMaxUndoRoundCount();
	}

	protected UndoManager<R> getUndoManager() {
		return undoManager;
	}

	protected int maxAvailable() {
		return 99;
	}

	protected int minAvailable() {
		return 0;
	}

	protected int defaultValue() {
		return minAvailable();
	}
}
