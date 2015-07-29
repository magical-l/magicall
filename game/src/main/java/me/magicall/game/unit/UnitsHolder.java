package me.magicall.game.unit;

import java.util.Collection;

import me.magicall.game.map.Coordinate;
import me.magicall.game.map.NoSuchUnitException;

/**
 * 坐标-单位的对应者。一个坐标上可能有多个单位，棋类游戏通常只有一个。
 * 
 * @author MaGiCalL
 */
public interface UnitsHolder {

	Collection<? extends Unit> getUnits(Coordinate coordinate);

	Unit getFirstUnit(Coordinate coordinate);

	void addUnits(final Coordinate coordinate, final Collection<? extends Unit> units) throws NoSuchUnitException;

	void addUnits(final Coordinate coordinate, final Unit... units) throws NoSuchUnitException;

	void addUnit(final Coordinate coordinate, final Unit unit) throws NoSuchUnitException;

	void removeUnits(Coordinate coordinate, final Collection<? extends Unit> units) throws NoSuchUnitException;

	void removeUnits(Coordinate coordinate, final Unit... units) throws NoSuchUnitException;

	void removeUnit(Coordinate coordinate, Unit unit) throws NoSuchUnitException;

	void removeAllUnits(Coordinate coordinate);

}