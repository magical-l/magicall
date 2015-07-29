package me.magicall.game.data;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import me.magicall.coll.CollFactory;
import me.magicall.coll.CollFactory.L;
import me.magicall.coll.CollectionFactory;
import me.magicall.game.map.Coordinate;
import me.magicall.game.map.NoSuchUnitException;
import me.magicall.game.unit.Unit;
import me.magicall.game.unit.UnitsHolder;
import me.magicall.util.kit.Kits;

public class UnitsMapManager implements UnitsHolder, Cloneable {

	private Map<Coordinate, Collection<Unit>> unitsInMap;

	private CollectionFactory collFactory;

	public UnitsMapManager(final CollectionFactory collectionFactory) {
		super();
		collFactory = collectionFactory;
		unitsInMap = collFactory.newMap();
	}

	@Override
	public Collection<Unit> getUnits(final Coordinate coordinate) {
		Collection<Unit> units = unitsInMap.get(coordinate);
		if (units == null) {
			units = collFactory.newLinkedList();
			unitsInMap.put(coordinate, units);
		}
		return Kits.COLL.forceSuit(units);
	}

	public CollectionFactory getCollFactory() {
		return collFactory;
	}

	public void setCollFactory(final CollectionFactory collFactory) {
		this.collFactory = collFactory;
	}

	@Override
	public void addUnits(final Coordinate coordinate, final Collection<? extends Unit> units) throws NoSuchUnitException {
		unitsInMap.get(coordinate).addAll(units);
	}

	@Override
	public void removeUnits(final Coordinate coordinate, final Collection<? extends Unit> units) throws NoSuchUnitException {
		unitsInMap.get(coordinate).removeAll(units);
	}

	@Override
	public void addUnit(final Coordinate coordinate, final Unit unit) throws NoSuchUnitException {
		getUnits(coordinate).add(unit);
	}

	@Override
	public void addUnits(final Coordinate coordinate, final Unit... units) throws NoSuchUnitException {
		getUnits(coordinate).addAll(L.asList(units));
	}

	@Override
	public Unit getFirstUnit(final Coordinate coordinate) {
		final Collection<Unit> units = getUnits(coordinate);
		return Kits.COLL.isEmpty(units) ? null : units.iterator().next();
	}

	@Override
	public void removeAllUnits(final Coordinate coordinate) {
		unitsInMap.remove(coordinate);
	}

	@Override
	public void removeUnit(final Coordinate coordinate, final Unit unit) throws NoSuchUnitException {
		getUnits(coordinate).remove(unit);
	}

	@Override
	public void removeUnits(final Coordinate coordinate, final Unit... units) throws NoSuchUnitException {
		getUnits(coordinate).removeAll(L.asList(units));
	}

	@Override
	protected UnitsMapManager clone() throws CloneNotSupportedException {
		final UnitsMapManager rt = (UnitsMapManager) super.clone();
		rt.unitsInMap = collFactory.newMap();
		for (final Entry<Coordinate, Collection<Unit>> e : unitsInMap.entrySet()) {
			final Collection<Unit> c = collFactory.newLinkedList();
			c.addAll(e.getValue());
			rt.unitsInMap.put(e.getKey(), c);
		}
		return rt;
	}
}
