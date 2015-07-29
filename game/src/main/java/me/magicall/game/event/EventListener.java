package me.magicall.game.event;

/**
 * 事件监听器
 * 
 * @author MaGiCalL
 * @param <T>
 */
public interface EventListener<T> {

	/**
	 * 在触发事件前做的事情
	 * 
	 * @param event
	 */
	void before(Event<T> event);

	/**
	 * 在触发事件后做的事情
	 * 
	 * @param event
	 */
	void after(Event<T> event);
}
