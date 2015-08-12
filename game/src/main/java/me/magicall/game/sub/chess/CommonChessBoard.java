package me.magicall.game.sub.chess;

import java.util.Collection;

import me.magicall.coll.CollectionFactory;
import me.magicall.coll.CommonCollectionFactory;
import me.magicall.game.Game;
import me.magicall.game.data.SingleUnitMapManager;
import me.magicall.game.map.BaseGamingMap;
import me.magicall.game.map.Coordinate;
import me.magicall.game.map.GameMap;
import me.magicall.game.map.GamingMap;
import me.magicall.game.map.NoSuchUnitException;
import me.magicall.game.sub.chess.util.ChessBoardDisplayer;
import me.magicall.game.unit.Unit;
import me.magicall.game.unit.UnitsHolder;

public class CommonChessBoard<G extends Game, M extends GameMap, U extends Chessman> //
		extends ChessBoardTemplate implements GamingMap {

	private String name;

	protected int maxPlayersCount;

	protected int rowsCount;
	protected int columnsCount;

	protected Position[][] positions;

	protected BaseGamingMap<G, M, Position, U> baseGamingMap;

	public CommonChessBoard(final int maxPlayersCount, final int size) {
		this(maxPlayersCount, size, size);
	}

	public CommonChessBoard(final int maxPlayersCount, final int rowsCount, final int columnsCount) {
		this.maxPlayersCount = maxPlayersCount;

		this.rowsCount = rowsCount;
		this.columnsCount = columnsCount;

		positions = Position.buildPositions(rowsCount, columnsCount);

		baseGamingMap = new BaseGamingMap<>();
		baseGamingMap.setUnitsHolder(buildUnitsHolder());
	}

	public CommonChessBoard(final CommonChessBoard<G, M, U> other) {
		maxPlayersCount = other.maxPlayersCount;
		rowsCount = other.rowsCount;
		columnsCount = other.columnsCount;
		baseGamingMap = new BaseGamingMap<>(other.baseGamingMap);
		positions = other.positions.clone();
	}

	public void setUnit(final Coordinate position, final U unit) {
		final SingleUnitMapManager<Coordinate, Unit> uh = baseGamingMap.getUnitsHolder();
		uh.setUnit(position, unit);
	}

	protected UnitsHolder buildUnitsHolder() {
		return new SingleUnitMapManager<Position, U>(getCollectionFactory());
	}

	protected CollectionFactory getCollectionFactory() {
		return CommonCollectionFactory.INSTANCE;
	}

	@Override
	public Position getCoordinate(final int... coordinateNumbers) {
		return positions[coordinateNumbers[0]][coordinateNumbers[1]];
	}

	public void setGame(final G game) {
		baseGamingMap.setGame(game);
	}

	@Override
	public G getGame() {
		return baseGamingMap.getGame();
	}

	@Override
	public void addUnit(final Coordinate coordinate, final Unit unit) throws NoSuchUnitException {
		baseGamingMap.addUnit(coordinate, unit);
	}

	@Override
	public void addUnits(final Coordinate coordinate, final Collection<? extends Unit> units) throws NoSuchUnitException {
		baseGamingMap.addUnits(coordinate, units);
	}

	@Override
	public void addUnits(final Coordinate coordinate, final Unit... units) throws NoSuchUnitException {
		baseGamingMap.addUnits(coordinate, units);
	}

	@Override
	public U getFirstUnit(final Coordinate coordinate) {
		return baseGamingMap.getFirstUnit(coordinate);
	}

	@Override
	public Collection<U> getUnits(final Coordinate coordinate) {
		return baseGamingMap.getUnits(coordinate);
	}

	@Override
	public void removeAllUnits(final Coordinate coordinate) {
		baseGamingMap.removeAllUnits(coordinate);
	}

	@Override
	public void removeUnit(final Coordinate coordinate, final Unit unit) throws NoSuchUnitException {
		baseGamingMap.removeUnit(coordinate, unit);
	}

	@Override
	public void removeUnits(final Coordinate coordinate, final Collection<? extends Unit> units) throws NoSuchUnitException {
		baseGamingMap.removeUnits(coordinate, units);
	}

	@Override
	public void removeUnits(final Coordinate coordinate, final Unit... units) throws NoSuchUnitException {
		baseGamingMap.removeUnits(coordinate, units);
	}

	@Override
	public int getColumnsCount() {
		return columnsCount;
	}

	@Override
	public int getRowsCount() {
		return rowsCount;
	}

	@Override
	public int getMaxPlayersCount() {
		return maxPlayersCount;
	}

	protected ChessBoardDisplayer getChessBoardDisplayer() {
		return ChessBoardDisplayer.INSTANCE;
	}

	@Override
	public String toString() {
		final ChessBoardDisplayer chessBoardDisplayer = getChessBoardDisplayer();
		final char[][] chars = chessBoardDisplayer.toCharSquare(this);//, blankCountBetweenNeighborCross());
		traverse(new ChessBoardTraverseHandler() {
			@Override
			protected boolean handle(final Position position) {
				final Chessman chessman = getFirstUnit(position);
				if (chessman != null) {
					chars[positionRowToBoardRowIndex(position.getRow())][positionColumnToBoardColumnIndex(position.getColumn())]//
					= chessman.getShowChar();
				}
				return true;
			}
		});
		return chessBoardDisplayer.toString(chars);
	}

	protected int blankCountBetweenNeighborCross() {
		return 0;
	}

	protected int positionRowToBoardRowIndex(final int positionRow) {
		return positionRow;
	}

	protected int positionColumnToBoardColumnIndex(final int positionColumn) {
		return positionColumn;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}