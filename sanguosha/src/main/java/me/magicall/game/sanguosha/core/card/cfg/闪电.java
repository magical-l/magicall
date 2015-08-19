package me.magicall.game.sanguosha.core.card.cfg;

import me.magicall.game.card.Card;
import me.magicall.game.card.CardCfg;
import me.magicall.game.sanguosha.core.area.JudgementArea;
import me.magicall.game.sanguosha.core.card.CardTypes;
import me.magicall.game.sanguosha.core.card.Flower;
import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.gaming.target.Selector;
import me.magicall.game.sanguosha.core.gaming.target.Selectors;
import me.magicall.game.sanguosha.core.player.GamingPlayer;
import me.magicall.game.sanguosha.core.skill.AbsTargetingHeroEffect;
import me.magicall.game.sanguosha.core.skill.Effect;
import me.magicall.game.sanguosha.core.skill.Skill;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.Collection;
import java.util.List;

/**
 * @author Liang Wenjian
 */
public class 闪电 extends CardCfg {

    public 闪电(final int point, final Flower flower) {
        super(CardTypes.延时锦囊, point, flower, new Skill_闪电());
    }

    private static class Skill_闪电 implements Skill {
        @Override
        public Effect<?> action(final Sanguosha game, final Hero user, final List<Hero> targets,
                                final Collection<Card> resources) {
            return new Effect_闪电(game, user.getPlayer(), this, resources, targets);
        }

        @Override
        public Selector getTargetSelector() {
            return Selectors.SELF;
        }
    }

    private static class Effect_闪电 extends AbsTargetingHeroEffect {

        public Effect_闪电(final Sanguosha game, final GamingPlayer user, final Skill skill,
                         final Collection<Card> resource, final List<Hero> targets) {
            super(game, user, skill, resource, targets);
        }

        @Override
        public void doEffect() {
            targets.forEach(hero -> {
                final JudgementArea judgement = hero.getJudgement();
                if (judgement.canGain(resources)) {
                    judgement.gain(resources);
                }
            });
        }
    }
    //TODO:判定生效
}
