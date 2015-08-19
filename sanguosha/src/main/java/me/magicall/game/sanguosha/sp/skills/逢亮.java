package me.magicall.game.sanguosha.sp.skills;

import me.magicall.game.card.Card;
import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.gaming.target.Selector;
import me.magicall.game.sanguosha.core.gaming.target.Selectors;
import me.magicall.game.sanguosha.core.skill.AbsTargetingHeroEffect;
import me.magicall.game.sanguosha.core.skill.Effect;
import me.magicall.game.sanguosha.core.skill.Skill;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.Collection;
import java.util.List;

/**
 * 觉醒技，当你进入濒死状态时，你减1点体力上限并将体力值回复至2点，然后获得技能"挑衅"，并将技能"困奋"改为非锁定技
 *
 * @author Liang Wenjian
 */
public class 逢亮 implements Skill {

    public 逢亮() {
        super();
    }

    @Override
    public void init(final Sanguosha game, final Hero owner) {
        game.hook(null);//TODO
    }

    @Override
    public Effect<?> action(final Sanguosha game, final Hero user, final List<Hero> targets,
                            final Collection<Card> resources) {
        return new Effect_困奋(game, user, this, resources, targets);
    }

    @Override
    public Selector getTargetSelector() {
        return Selectors.SELF;
    }

    private static class Effect_困奋 extends AbsTargetingHeroEffect {

        public Effect_困奋(final Sanguosha game, final Hero user, final Skill skill, final Collection<Card> resources,
                         final List<Hero> targets) {
            super(game, user.getPlayer(), skill, resources, targets);
        }

        @Override
        public void doEffect() {
            final Hero hero = user.getHero();
            hero.setHpUpperBound(hero.getHpUpperBound() - 1);
            hero.setHp(2);
            final Collection<Skill> skills = hero.getSkills();
            skills.forEach(e -> {
                if (e instanceof 困奋) {
                    ((困奋) e).setIs锁定技(false);
                }
            });
            skills.add(null);//TODO:挑衅
        }
    }
}
