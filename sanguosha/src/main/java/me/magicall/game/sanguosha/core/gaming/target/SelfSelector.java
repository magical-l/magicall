package me.magicall.game.sanguosha.core.gaming.target;

import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.player.GamingPlayer;
import me.magicall.game.sanguosha.core.skill.Skill;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.Collections;
import java.util.List;

/**
 * 直接选择自己为目标的选择器。适用于【桃】、【酒】、【闪电】、装备等。
 *
 * @author Liang Wenjian
 */
public class SelfSelector implements Selector {

    public SelfSelector() {
        super();
    }

    @Override
    public List<Hero> getTarget(final Sanguosha game, final GamingPlayer user, final Skill skill) {
        return Collections.singletonList(user.getHero());
    }
}
