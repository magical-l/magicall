package me.magicall.game.event;

/**
 * 事件发生器。包括创建事件和执行事件。执行事件前后会触发事件监听器，但这两步是由EventListenerManager来执行。
 * 
 * @author MaGiCalL
 * @param <T>
 */
public interface EventHandler<T> {

	Event<T> createEvent();

	void handleEvent(Event<T> event);
}
