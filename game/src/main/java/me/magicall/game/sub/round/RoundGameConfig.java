package me.magicall.game.sub.round;

import me.magicall.game.config.GameConfig;

public interface RoundGameConfig extends GameConfig {

	@Override
	RoundGameMap getMap();
}
