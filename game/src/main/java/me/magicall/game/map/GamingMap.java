package me.magicall.game.map;


import me.magicall.game.Game;
import me.magicall.game.unit.UnitsHolder;

public interface GamingMap extends GameMap, UnitsHolder {

	Game getGame();
}
