package me.magicall.game.sanguosha.core.gaming.round;

import me.magicall.game.card.Event;

/**
 * @author Liang Wenjian
 */
public class TurnEndEvent extends Event {

    private static final long serialVersionUID = 1734293702380473591L;

    public TurnEndEvent(final HeroTurn source) {
        super(source);
    }
}
