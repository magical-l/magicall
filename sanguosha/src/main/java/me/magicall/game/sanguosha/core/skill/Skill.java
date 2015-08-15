package me.magicall.game.sanguosha.core.skill;

import me.magicall.game.Targetable;
import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.unit.Hero;
import me.magicall.mark.Named;

import java.util.List;

/**
 * 技能。
 *
 * @author Liang Wenjian
 */
public interface Skill extends Named, Targetable {

    void action(Sanguosha game, Hero user, List<Hero> targets);

    List<Hero> selectTargets(Sanguosha game, Hero user);
}
