package me.magicall.game.map;

import java.util.Collection;

import me.magicall.mark.Named;

/**
 * 地图
 * 
 * @author MaGiCalL
 */
public interface GameMap extends Named {

	/**
	 * 地图遍历器，用于GameMap.traverse()方法
	 * 
	 * @author MaGiCalL
	 */
	@FunctionalInterface
	public static interface MapTraverseHandler {
		boolean handle(Coordinate coordinate);
	}

	/**
	 * 几维地图
	 * 
	 * @return
	 */
	int getDimCount();

	/**
	 * 本地图最多可供多少玩家进行游戏
	 * 
	 * @return
	 */
	int getMaxPlayersCount();

	/**
	 * 地图宽度
	 * 
	 * @return
	 */
	int getWidth();

	/**
	 * 地图高度
	 * 
	 * @return
	 */
	int getHeight();

	int getCoordinateCount();

	/**
	 * 获取一个坐标
	 * 
	 * @param coordinateNumbers
	 * @return
	 */
	Coordinate getCoordinate(final int... coordinateNumbers);

	void traverse(Collection<? extends MapTraverseHandler> traverseHandlers);

	void traverse(MapTraverseHandler... traverseHandlers);

	void traverse(MapTraverseHandler traverseHandler);

}
