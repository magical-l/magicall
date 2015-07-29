package me.magicall.game.event;

import java.util.Collection;

/**
 * 事件.事件可以是一个技能的一次施放效果、一个单位的死亡、一场游戏的结束等等。
 * 
 * @author MaGiCalL
 * @param <T> 事件
 */
public interface Event<T> {

	/**
	 * 事件的来源,可能是单位、游戏、技能、物品、环境、地图、甚至是事件本身。
	 * 
	 * @return
	 */
	T getEventSource();

	/**
	 * 触发事件的第一类目标，是事件的“主语”（Subject）。
	 * 
	 * @return
	 */
	Collection<? extends EventTarget> getEventTargets1();

	/**
	 * 触发事件的第二类目标，是事件的“宾语”（Object）。有的事件没有宾语。
	 * 
	 * @return
	 */
	Collection<? extends EventTarget> getEventTargets2();
}
