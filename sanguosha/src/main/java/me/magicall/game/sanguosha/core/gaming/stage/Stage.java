package me.magicall.game.sanguosha.core.gaming.stage;

import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.unit.Hero;

/**
 * 阶段。
 *
 * @author Liang Wenjian
 */
public interface Stage {

    void play();

    Hero getOwner();

    Sanguosha getGame();
}
