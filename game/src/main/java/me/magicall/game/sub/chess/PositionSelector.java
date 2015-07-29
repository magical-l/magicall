/**
 * 
 */
package me.magicall.game.sub.chess;

import java.util.Collection;
import java.util.regex.Pattern;

import me.magicall.coll.CollFactory;
import me.magicall.coll.CollFactory.L;
import me.magicall.game.Game;
import me.magicall.game.map.Coordinate;
import me.magicall.game.skill.Command;
import me.magicall.game.skill.NoTargetToBeSelectingException;
import me.magicall.game.skill.TargetSelector;
import me.magicall.util.kit.Kits;

public class PositionSelector implements TargetSelector {
	protected static final Pattern POSITION_PATTERN = Pattern.compile("\\s*,\\s*");

	@Override
	public Collection<Position> select(final Game game, final Command command) throws NoTargetToBeSelectingException {
		final String string = (String) command.getData();
		try {
			final String[] pos = getPositionInputSplitPattern().split(string);
			if (pos.length != 2) {
				throw new NoTargetToBeSelectingException("输入有误，请输入两个数字。row,column");
			}
			final int row = Kits.INT.fromString(pos[0]);
			final int column = Kits.INT.fromString(pos[1]);
			final Coordinate position = game.getGamingMap().getCoordinate(row, column);
			return Kits.COLL.forceSuit(L.asList(position));
		} catch (final Exception e) {
			throw new NoTargetToBeSelectingException("输入有误，请输入两个数字。row,column", e);
		}
	}

	protected Pattern getPositionInputSplitPattern() {
		return POSITION_PATTERN;
	}
}