package me.magicall.game.sub.round.abs;

import java.util.Collection;
import java.util.List;

import me.magicall.game.abs.GameTemplate;
import me.magicall.game.player.PlayerRole;
import me.magicall.game.sub.round.CommonRound;
import me.magicall.game.sub.round.Round;
import me.magicall.game.sub.round.RoundGame;
import me.magicall.game.sub.round.RoundGamePlayerRole;
import me.magicall.game.sub.round.plugin.RoundPlugin;
import me.magicall.util.kit.Kits;

public abstract class RoundGameTemplate extends GameTemplate implements RoundGame {

	@Override
	public int getRoundsCount() {
		return getRounds().size();
	}

	@Override
	public Round getLastRound() {
		final List<? extends Round> rounds = getRounds();
		return Kits.LIST.isEmpty(rounds) ? null : rounds.get(rounds.size() - 1);
	}

	@Override
	protected void run() {
		//创建一个新的回合
		final Round round = newRound();
		//设置为当前回合
		setCurRound(round);
		//发动插件-回合开始阶段
		pluginsStartingRound(round);
		//玩家操作
		playerPlay(round);
		//发动插件-记录回合阶段
		pluginsAddingRound(round);
		//记录回合
		addRound(round);
		//发动插件-回合结束阶段
		pluginsEndingRound(round);
		//回合结束
		endRound(round);
		//发动插件-回合结束完毕阶段
		pluginsEndedRound(round);
	}

	/**
	 * 玩家操作
	 * 
	 * @param round
	 */
	protected void playerPlay(final Round round) {
		for (final PlayerRole playerRole : getPlayerRoles()) {
			((RoundGamePlayerRole) playerRole).play(round);
		}
	}

	/**
	 * 当前回合结束后触发插件
	 * 
	 * @param round
	 */
	protected void pluginsEndedRound(final Round round) {
		final Collection<RoundPlugin> roundPlugins = getRoundPlugins();
		for (final RoundPlugin plugin : roundPlugins) {
			plugin.endedRound(this, round);
		}
	}

	/**
	 * 当前回合结束时触发插件
	 * 
	 * @param round
	 */
	protected void pluginsEndingRound(final Round round) {
		final Collection<RoundPlugin> roundPlugins = getRoundPlugins();
		for (final RoundPlugin plugin : roundPlugins) {
			plugin.endingRound(this, round);
		}
	}

	/**
	 * 记录回合时触发插件
	 * 
	 * @param round
	 */
	protected void pluginsAddingRound(final Round round) {
		final Collection<RoundPlugin> roundPlugins = getRoundPlugins();
		for (final RoundPlugin plugin : roundPlugins) {
			plugin.addingRound(this, round);
		}
	}

	/**
	 * 回合开始时触发插件
	 * 
	 * @param round
	 */
	protected void pluginsStartingRound(final Round round) {
		final Collection<RoundPlugin> roundPlugins = getRoundPlugins();
		for (final RoundPlugin plugin : roundPlugins) {
			plugin.startingRound(this, round);
		}
	}

	protected Collection<RoundPlugin> getRoundPlugins() {
		return Kits.LIST.emptyValue();
	}

	/**
	 * 当前回合结束
	 * 
	 * @param round
	 */
	protected void endRound(final Round round) {
	}

	/**
	 * 当前回合完成时记录当前回合
	 * 
	 * @param round
	 */
	protected abstract void addRound(Round round);

	/**
	 * 创建一个回合
	 * 
	 * @return
	 */
	protected Round newRound() {
		return new CommonRound();
	}

	/**
	 * 设置当前回合
	 * 
	 * @param round
	 */
	protected abstract void setCurRound(Round round);
}
