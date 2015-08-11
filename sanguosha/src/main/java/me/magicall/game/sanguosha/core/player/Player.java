package me.magicall.game.sanguosha.core.player;

import me.magicall.game.sanguosha.core.gaming.option.Options;
import me.magicall.game.sanguosha.core.gaming.option.Selection;

/**
 * 玩家的io接口。
 *
 * @author Liang Wenjian
 */
@FunctionalInterface
public interface Player {

    <T extends Selection> T requireInput(final Options<T> options);
}
