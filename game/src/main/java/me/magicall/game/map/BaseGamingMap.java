package me.magicall.game.map;

import java.util.Collection;

import me.magicall.coll.CollectionFactory;
import me.magicall.coll.CommonCollectionFactory;
import me.magicall.game.Game;
import me.magicall.game.unit.Unit;
import me.magicall.game.unit.UnitsHolder;
import me.magicall.util.ObjectUtil;
import me.magicall.util.kit.Kits;

public class BaseGamingMap<G extends Game, M extends GameMap, P extends Coordinate, //
U extends Unit> //
		extends GameMapTemplate implements GamingMap {

	protected G game;
	protected M map;
	protected UnitsHolder unitsHolder;

	public BaseGamingMap() {
		super();
	}

	public BaseGamingMap(final BaseGamingMap<G, M, P, U> baseGamingMap) {
		super();
		copyFrom(baseGamingMap);
	}

	public void copyFrom(final BaseGamingMap<G, M, P, U> baseGamingMap) {
		game = ObjectUtil.tryCopy(baseGamingMap.game);
		map = ObjectUtil.tryCopy(baseGamingMap.map);
		unitsHolder = ObjectUtil.tryCopy(baseGamingMap.unitsHolder);
	}

	protected CollectionFactory getCollectionFactory() {
		return CommonCollectionFactory.INSTANCE;
	}

	@Override
	public void addUnits(final Coordinate coordinate, final Collection<? extends Unit> units) throws NoSuchUnitException {
		unitsHolder.addUnits(coordinate, units);
	}

	@SuppressWarnings("unchecked")
	@Override
	public U getFirstUnit(final Coordinate coordinate) {
		final Collection<U> units = (Collection<U>) unitsHolder.getUnits(coordinate);
		return Kits.COLL.isEmpty(units) ? null : units.iterator().next();
	}

	@Override
	public G getGame() {
		return game;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<U> getUnits(final Coordinate coordinate) {
		return (Collection<U>) unitsHolder.getUnits(coordinate);
	}

	@Override
	public void removeUnits(final Coordinate coordinate, final Collection<? extends Unit> units) throws NoSuchUnitException {
		unitsHolder.removeUnits(coordinate, units);
	}

	@SuppressWarnings("unchecked")
	@Override
	public P getCoordinate(final int... coordinateNumbers) {
		return (P) map.getCoordinate(coordinateNumbers);
	}

	@Override
	public int getCoordinateCount() {
		return map.getCoordinateCount();
	}

	@Override
	public int getDimCount() {
		return map.getDimCount();
	}

	@Override
	public int getHeight() {
		return map.getHeight();
	}

	@Override
	public int getMaxPlayersCount() {
		return map.getMaxPlayersCount();
	}

	@Override
	public int getWidth() {
		return map.getWidth();
	}

	@Override
	public void traverse(final Collection<? extends MapTraverseHandler> traverseHandlers) {
		map.traverse(traverseHandlers);
	}

	public M getMap() {
		return map;
	}

	public void setMap(final M map) {
		this.map = map;
	}

	public void setGame(final G game) {
		this.game = game;
	}

	@Override
	public void addUnit(final Coordinate coordinate, final Unit unit) throws NoSuchUnitException {
		unitsHolder.addUnit(coordinate, unit);
	}

	@Override
	public void addUnits(final Coordinate coordinate, final Unit... units) throws NoSuchUnitException {
		unitsHolder.addUnits(coordinate, units);
	}

	@Override
	public void removeAllUnits(final Coordinate coordinate) {
		unitsHolder.removeAllUnits(coordinate);
	}

	@Override
	public void removeUnit(final Coordinate coordinate, final Unit unit) throws NoSuchUnitException {
		unitsHolder.removeUnit(coordinate, unit);
	}

	@Override
	public void removeUnits(final Coordinate coordinate, final Unit... units) throws NoSuchUnitException {
		unitsHolder.removeUnits(coordinate, units);
	}

	public <UH extends UnitsHolder> UH getUnitsHolder() {
		return Kits.OBJ.forceSuit(unitsHolder);
	}

	public void setUnitsHolder(final UnitsHolder unitsMapManager) {
		unitsHolder = unitsMapManager;
	}

	@Override
	public String getName() {
		return map.getName();
	}
}