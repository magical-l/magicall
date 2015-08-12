package me.magicall.game.io;

/**
 * 输入处理器，是GameInput的回调
 * 
 * @author MaGiCalL
 */
@FunctionalInterface
public interface InputHandler {

	void handle(Object nextInput) throws OperatingException;
}
