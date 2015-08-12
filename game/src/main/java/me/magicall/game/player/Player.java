package me.magicall.game.player;

import me.magicall.game.io.GameInput;
import me.magicall.game.io.GameOutput;
import me.magicall.mark.Named;

/**
 * 玩家类，是现实中的“玩家”（可能是人，也可能是AI）在程序中的代理接口。
 * 与具体一场游戏无关的对象。无论游戏是否开始是否结束，玩家始终是独立的对象。
 * 使用例子：用户在服务器登录后即生成相应的玩家对象，input/output可能是网络套接字之类。
 * 
 * @author MaGiCalL
 */
public interface Player extends Named {

	/**
	 * 玩家的输入端。可能是鼠标、键盘、麦克风、遥控器等等。
	 * 
	 * @return
	 */
	GameInput getGameInput();

	/**
	 * 玩家的输出端。可能是屏幕、音响、灯、耳机等等。
	 * 
	 * @return
	 */
	GameOutput getGameOutput();

}
