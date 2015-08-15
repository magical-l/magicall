package me.magicall.game.sanguosha.core.gaming.target;

import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.player.GamingPlayer;
import me.magicall.game.sanguosha.core.skill.Skill;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.List;

/**
 * 选择器。
 *
 * @author Liang Wenjian
 */
@FunctionalInterface
public interface Selector {

    List<Hero> getTarget(Sanguosha game, GamingPlayer user, Skill skill);
}
