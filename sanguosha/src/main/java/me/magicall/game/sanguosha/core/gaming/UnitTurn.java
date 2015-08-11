package me.magicall.game.sanguosha.core.gaming;

import me.magicall.game.sanguosha.core.hero.base.Unit;

/**
 * @author Liang Wenjian
 */
public interface UnitTurn {

    void play();

    Unit getOwner();
}
