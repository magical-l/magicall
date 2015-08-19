package me.magicall.game.sanguosha.core.gaming;

import me.magicall.game.card.Event;

/**
 * @author Liang Wenjian
 */
public interface Hook<E extends Event> {
    Class<E> getEventClass();

    void before(E event);

    void after(E event);
}
