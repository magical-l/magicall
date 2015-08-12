package me.magicall.game.map;


/**
 * 坐标。表示单位在地图上的位置。
 * 
 * @author MaGiCalL
 */
@FunctionalInterface
public interface Coordinate {

	int[] getCoordinateNums();
}
