package me.magicall.sha;

public interface Event {

	void exe();

	void addEventListener(EventListener eventListener);

	void removeEventListener(EventListener eventListener);
}
