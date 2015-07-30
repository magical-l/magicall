package me.magicall.game.xiangqi.skill;

import me.magicall.game.Game;
import me.magicall.game.io.OperatingException;
import me.magicall.game.player.Player;
import me.magicall.game.skill.SelectingTargetsSkillTemplate;
import me.magicall.game.sub.chess.Position;
import me.magicall.game.unit.Unit;
import me.magicall.game.xiangqi.Board;

public class Move extends SelectingTargetsSkillTemplate {

	@Override
	protected void action(final Game game, final Player player, final Position position) throws OperatingException {
		// TODO Auto-generated method stub
		final Board board = (Board) game.getGamingMap();
		final Unit unit = board.getFirstUnit(position);

	}

}
