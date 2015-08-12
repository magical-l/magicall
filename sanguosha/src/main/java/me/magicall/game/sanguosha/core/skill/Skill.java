package me.magicall.game.sanguosha.core.skill;

import me.magicall.game.Targetable;
import me.magicall.mark.Named;

/**
 * 技能。
 *
 * @author Liang Wenjian
 */
public interface Skill extends Named, Targetable {

    Effect action();
}
