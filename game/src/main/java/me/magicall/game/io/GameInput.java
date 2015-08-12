package me.magicall.game.io;

/**
 * 游戏输入端。是与用户交互的接口。对于游戏程序来说是输入端；对用户来说是输出端。
 * 如：鼠标、键盘、麦克风、遥控器、手柄、触摸屏等等。
 * 
 * @author MaGiCalL
 */
@FunctionalInterface
public interface GameInput {
	/**
	 * 向输入端请求一个输入,然后回调InputHandler参数的方法
	 * 
	 * @param inputHandler
	 * @throws OperatingException
	 */
	void requestInput(final InputHandler inputHandler) throws OperatingException;
}
