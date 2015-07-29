package me.magicall.game.xiangqi;

import me.magicall.game.Game;
import me.magicall.game.player.PlayerRole;
import me.magicall.game.player.Team;
import me.magicall.game.sub.round.CommonRound;
import me.magicall.game.sub.round.abs.AbsRoundGamePlayerRole;
import me.magicall.game.util.GameUtil;

public class XiangqiPlayerRole extends AbsRoundGamePlayerRole<Xiangqi, CommonRound>//
		implements Team {

//	public static final XiangqiPlayerRole RED = new XiangqiPlayerRole("红方", 1);
//	public static final XiangqiPlayerRole BLACK = new XiangqiPlayerRole("黑方", 1);

	private final XiangqiPlayerRole[] roles;

	public XiangqiPlayerRole(final Xiangqi game, final String name, final int order) {
		super(game, name, order);
		roles = new XiangqiPlayerRole[] { this };
	}

	@Override
	protected void playInternal(final CommonRound round) {
		// TODO Auto-generated method stub

	}

	@Override
	public PlayerRole[] getPlayerRoles() {
		return roles;
	}

	@Override
	public int compareTo(final Team o) {
		return GameUtil.HAS_ORDER_COMPARATOR.compare(this, o);
	}

	@Override
	public void setName(final String name) {

	}

	@Override
	public Game getGame() {
		// TODO Auto-generated method stub
		return null;
	}

}
