package me.magicall.game.xiangqi;

import me.magicall.game.player.PlayerRole;
import me.magicall.game.player.Team;
import me.magicall.game.sub.round.CommonRound;
import me.magicall.game.sub.round.abs.AbsRoundGame;
import me.magicall.game.xiangqi.config.Config;

public class Xiangqi extends AbsRoundGame<Config, Board, Board, CommonRound>{// implements MatchingGame {

	private final Team[] teams;

	private Team winnerTeam;

	public Xiangqi(final Config configImpl) {
		super();
		final Team[] teams = {};// XiangqiPlayerRole.RED, XiangqiPlayerRole.BLACK };
		this.teams = teams;
	}

	@Override
	protected PlayerRole newPlayerRole() {
		return null;//TODO
	}

	@Override
	protected boolean checkGameOver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Team[] getTeams() {
		return teams;
	}

	@Override
	public Team getWinnerTeam() {
		return winnerTeam;
	}

	@Override
	public PlayerRole[] getWinners() {
		return getWinnerTeam().getPlayerRoles();
	}

}
