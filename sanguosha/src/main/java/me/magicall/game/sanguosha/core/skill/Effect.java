package me.magicall.game.sanguosha.core.skill;

import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.Collection;

/**
 * 技能的效果。
 *
 * @author Liang Wenjian
 */
@FunctionalInterface
public interface Effect {
    /**
     * 起效。要对目标进行处理。
     *
     * @param targets
     */
    void work(final Collection<Hero> targets);
}
