package me.magicall.game.sanguosha.core.gaming.round;

import me.magicall.game.card.Unit;

/**
 * @author Liang Wenjian
 */
public interface UnitTurn {

    void play();

    Unit getOwner();
}
