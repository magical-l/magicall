package me.magicall.game.sanguosha.界线突破;

import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.skill.Effect;
import me.magicall.game.sanguosha.core.skill.Skill;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.List;

/**
 * @author Liang Wenjian
 */
public class MuniuLiumaSkill implements Skill {

    public MuniuLiumaSkill() {
        super();
    }

    @Override
    public String getName() {
        return "木牛流马";
    }

    @Override
    public Effect action(final Sanguosha sanguosha, final Hero user, List<Hero> targets) {
        return null;//TODO
    }
}
