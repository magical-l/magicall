package me.magicall.game.sanguosha.core.card.cfg;

import me.magicall.game.card.CardCfg;
import me.magicall.game.sanguosha.core.card.CardTypes;
import me.magicall.game.sanguosha.core.card.Flower;
import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.skill.Skill;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.List;

/**
 * @author Liang Wenjian
 */
public class 丈八蛇矛 extends CardCfg{

    public 丈八蛇矛(final int point, final Flower flower) {
        super(CardTypes.WEAPON, point, flower, new Skill_丈八蛇矛());//TODO
    }

    private static class Skill_丈八蛇矛 implements Skill {
        @Override
        public void action(final Sanguosha game, final Hero user, List<Hero> targets) {
            //TODO
        }

        @Override
        public List<Hero> selectTargets(Sanguosha game, Hero user) {
            return null;//TODO
        }

        @Override
        public String getName() {
            return null;
        }
    }
}
