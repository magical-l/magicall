package me.magicall.game.sanguosha.core.gaming.round;

import me.magicall.game.card.Event;

/**
 * @author Liang Wenjian
 */
public class RoundStartEvent extends Event {

    private static final long serialVersionUID = 6200653555136903343L;

    public RoundStartEvent(final SanguoshaRound source) {
        super(source);
    }
}
