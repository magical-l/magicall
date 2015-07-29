package me.magicall.game.data;

import java.util.Collection;
import java.util.Map;

import me.magicall.coll.CollFactory;
import me.magicall.coll.CollFactory.L;
import me.magicall.coll.CollectionFactory;
import me.magicall.game.map.Coordinate;
import me.magicall.game.map.NoSuchUnitException;
import me.magicall.game.unit.Unit;
import me.magicall.game.unit.UnitsHolder;
import me.magicall.tagInterfaces.Copyable;
import me.magicall.util.kit.Kits;

/**
 * 地图上每个坐标最多只有一个单位的UnitHolder
 * 
 * @author MaGiCalL
 * @param <P>
 * @param <U>
 */
public class SingleUnitMapManager<P extends Coordinate, U extends Unit> //
		implements UnitsHolder, Copyable<SingleUnitMapManager<P, U>> {

	private Map<Coordinate, Unit> unitsInMap;

	private CollectionFactory collFactory;

	public SingleUnitMapManager(final CollectionFactory collectionFactory) {
		super();
		collFactory = collectionFactory;
		unitsInMap = collFactory.newMap();
	}

	@Override
	public Collection<U> getUnits(final Coordinate coordinate) {
		final Unit unit = unitsInMap.get(coordinate);
		if (unit == null) {
			return Kits.LIST.emptyValue();
		} else {
			return Kits.LIST.forceSuit(L.asList(unit));
		}
	}

	public CollectionFactory getCollFactory() {
		return collFactory;
	}

	public void setCollFactory(final CollectionFactory collFactory) {
		this.collFactory = collFactory;
	}

	@Override
	public void addUnits(final Coordinate coordinate, final Collection<? extends Unit> units) throws NoSuchUnitException {
		final Unit unit = unitsInMap.get(coordinate);
		if (unit == null) {
			unitsInMap.put(coordinate, units.iterator().next());
		}
	}

	@Override
	public void removeUnits(final Coordinate coordinate, final Collection<? extends Unit> units) throws NoSuchUnitException {
		final Unit unit = unitsInMap.get(coordinate);
		if (unit == null) {
			return;
		}
		if (units.contains(unit)) {
			unitsInMap.remove(coordinate);
		}
	}

	@Override
	public void addUnit(final Coordinate coordinate, final Unit unit) throws NoSuchUnitException {
		final Unit old = unitsInMap.get(coordinate);
		if (old == null) {
			unitsInMap.put(coordinate, unit);
		}
	}

	@Override
	public void addUnits(final Coordinate coordinate, final Unit... units) throws NoSuchUnitException {
		final Unit old = unitsInMap.get(coordinate);
		if (old == null) {
			unitsInMap.put(coordinate, units[0]);
		}
	}

	@Override
	public U getFirstUnit(final Coordinate coordinate) {
		final Collection<U> units = getUnits(coordinate);
		return Kits.COLL.isEmpty(units) ? null : units.iterator().next();
	}

	@Override
	public void removeAllUnits(final Coordinate coordinate) {
		unitsInMap.remove(coordinate);
	}

	@Override
	public void removeUnit(final Coordinate coordinate, final Unit unit) throws NoSuchUnitException {
		if (unitsInMap.get(coordinate).equals(unit)) {
			unitsInMap.remove(coordinate);
		}
	}

	@Override
	public void removeUnits(final Coordinate coordinate, final Unit... units) throws NoSuchUnitException {
		removeUnits(coordinate, L.asList(units));
	}

	public void setUnit(final Coordinate coordinate, final Unit unit) {
		unitsInMap.put(coordinate, unit);
	}

	public void addAll(final Map<Coordinate, Unit> map) {
		unitsInMap.putAll(map);
	}

	public Map<P, U> getUnitsInMap() {
		return Kits.MAP.forceSuit(unitsInMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public SingleUnitMapManager<P, U> clone() {
		try {
			final SingleUnitMapManager<P, U> rt = (SingleUnitMapManager<P, U>) super.clone();
			rt.unitsInMap = collFactory.newMap();
			rt.unitsInMap.putAll(unitsInMap);
			return rt;
		} catch (final CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
