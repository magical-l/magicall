package me.magicall.game.xiangqi.config;

import java.util.ArrayList;
import java.util.List;

import me.magicall.game.config.GameOption;
import me.magicall.game.config.IllegalOptionValueException;
import me.magicall.game.config.OptionItem;
import me.magicall.game.io.GameOutput;
import me.magicall.game.xiangqi.Board;

public class MapOptionItem implements OptionItem<Board> {

	private final List<Board> maps;

	public MapOptionItem() {
		super();
		final List<Board> list = new ArrayList<>();
		maps = list;
	}

	@Override
	public Board checkLegal(final Object inputObject) throws IllegalOptionValueException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValue(final GameOption gameOption, final Board value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showAvailableValues(final GameOutput gameOutput) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
