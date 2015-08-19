package me.magicall.game.sanguosha.core.card.cfg;

import me.magicall.game.card.Card;
import me.magicall.game.card.CardCfg;
import me.magicall.game.sanguosha.core.card.CardTypes;
import me.magicall.game.sanguosha.core.card.Flower;
import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.gaming.target.Selector;
import me.magicall.game.sanguosha.core.skill.Effect;
import me.magicall.game.sanguosha.core.skill.Skill;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.Collection;
import java.util.List;

/**
 * @author Liang Wenjian
 */
public class 丈八蛇矛 extends CardCfg {

    public 丈八蛇矛(final int point, final Flower flower) {
        super(CardTypes.武器, point, flower, new Skill_丈八蛇矛());//TODO
    }

    private static class Skill_丈八蛇矛 implements Skill {

        @Override
        public String getName() {
            return null;
        }

        @Override
        public Effect<?> action(final Sanguosha game, final Hero user, final List<Hero> targets,
                                final Collection<Card> resources) {
            return null;//TODO
        }

        @Override
        public Selector getTargetSelector() {
            return null;//TODO
        }

        @Override
        public int getMinResourcesCount() {
            return 0;//TODO
        }

        @Override
        public Integer getMaxResourcesCount() {
            return null;//TODO
        }
    }
}
