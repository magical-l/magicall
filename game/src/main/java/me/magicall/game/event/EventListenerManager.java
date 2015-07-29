package me.magicall.game.event;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 事件监听器的管理器
 * 
 * @author MaGiCalL
 */
public class EventListenerManager {

	protected Map<Class<? extends Event<?>>, Collection<EventListener<?>>> map;

	public EventListenerManager() {
		this(new HashMap<>());
	}

	public EventListenerManager(final Map<Class<? extends Event<?>>, Collection<EventListener<?>>> map) {
		this.map = map;
	}

	public <T> void addEventListener(final Class<? extends Event<T>> eventClass, final EventListener<T> eventListener) {
		Collection<EventListener<?>> eventListeners = map.get(eventClass);
		if (eventListeners == null) {
			eventListeners = newEventListenerCollection();
			map.put(eventClass, eventListeners);
		}
		eventListeners.add(eventListener);
	}

	public void removeEventListener(final Class<? extends Event<?>> eventClass, final EventListener<?> eventListener) {
		final Collection<EventListener<?>> eventListeners = map.get(eventClass);
		if (eventListeners != null) {
			eventListeners.remove(eventListener);
		}
	}

	public void removeEventListeners(final Class<? extends Event<?>> eventClass) {
		map.remove(eventClass);
	}

	protected Collection<EventListener<?>> newEventListenerCollection() {
		return new LinkedList<>();
	}

	/**
	 * 主要方法。会调用EventHandler的createEvent方法创建一个事件；
	 * 触发监听此事件的所有事件监听器的前操作；
	 * 然后调用EventHandler的handleEvent方法；
	 * 最后触发此事件的所有事件监听器的后操作。
	 * 
	 * @param eventHandler
	 * @return 事件
	 */
	public <T> Event<T> handle(final EventHandler<T> eventHandler) {
		final Event<T> event = eventHandler.createEvent();

		before(eventHandler, event);

		eventHandler.handleEvent(event);

		after(eventHandler, event);

		return event;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected <T> Collection<EventListener<T>> getEventListeners(final Event<T> event) {
		final Collection<EventListener> rt = new LinkedList<>();
		for (final Entry<Class<? extends Event<?>>, Collection<EventListener<?>>> e : map.entrySet()) {
			final Class<?> c = e.getKey();
			if (c.isAssignableFrom(event.getClass())) {
				rt.addAll(e.getValue());
			}
		}
		return (Collection) rt;
	}

	protected <T> void before(final EventHandler<T> eventHandler, final Event<T> event) {
		final Collection<EventListener<T>> eventListeners = getEventListeners(event);
		for (final EventListener<T> listener : eventListeners) {
			listener.before(event);
		}
	}

	protected <T> void after(final EventHandler<T> eventHandler, final Event<T> event) {
		final Collection<EventListener<T>> eventListeners = getEventListeners(event);
		for (final EventListener<T> listener : eventListeners) {
			listener.after(event);
		}
	}
}
