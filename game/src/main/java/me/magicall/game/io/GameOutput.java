package me.magicall.game.io;

/**
 * 游戏输出端，是与用户交互的接口。对于游戏程序来说是输出端。
 * 
 * @author MaGiCalL
 */
@FunctionalInterface
public interface GameOutput {

	/**
	 * @param source 触发输出的对象，通常是“this”
	 * @param content 输出的内容
	 */
	void output(OutputSource source, Object content);
}
