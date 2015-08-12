package me.magicall.game.sanguosha.core.gaming.round;

import me.magicall.game.card.Event;

/**
 * @author Liang Wenjian
 */
public class TurnStartEvent extends Event {

    private static final long serialVersionUID = -700352516642712016L;

    public TurnStartEvent(final HeroTurn source) {
        super(source);
    }
}
