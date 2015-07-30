package me.magicall.game.xiangqi.config;

import me.magicall.game.sub.round.RoundGameConfig;
import me.magicall.game.xiangqi.Board;

public interface Config extends RoundGameConfig {

	@Override
	Board getMap();
}
