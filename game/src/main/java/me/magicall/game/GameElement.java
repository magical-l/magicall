package me.magicall.game;

/**
 * 一场游戏中的元素。
 * 
 * @author MaGiCalL
 */
@FunctionalInterface
public interface GameElement {

	/**
	 * 相应的游戏。
	 * 
	 * @return
	 */
	Game getGame();

}
