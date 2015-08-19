package me.magicall.game.sanguosha.sp.skills;

import me.magicall.game.card.Card;
import me.magicall.game.sanguosha.core.gaming.Hook;
import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.gaming.stage.StageStartEvent;
import me.magicall.game.sanguosha.core.gaming.target.Selector;
import me.magicall.game.sanguosha.core.gaming.target.Selectors;
import me.magicall.game.sanguosha.core.skill.AbsTargetingHeroEffect;
import me.magicall.game.sanguosha.core.skill.Effect;
import me.magicall.game.sanguosha.core.skill.Skill;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 锁定技，结束阶段开始时，你失去1点体力，然后摸两张牌
 *
 * @author Liang Wenjian
 */
public class 困奋 implements Skill {

    private boolean is锁定技 = true;

    public 困奋() {
        super();
    }

    @Override
    public void init(final Sanguosha game, final Hero owner) {
        game.hook(new Hook_困奋(this, owner, game));
    }

    @Override
    public boolean is锁定技() {
        return is锁定技;
    }

    public void setIs锁定技(final boolean is锁定技) {
        this.is锁定技 = is锁定技;
    }

    @Override
    public Effect<?> action(final Sanguosha game, final Hero user, final List<Hero> targets,
                            final Collection<Card> resources) {
        return new Effect_困奋(game, user, resources, targets);
    }

    @Override
    public Selector getTargetSelector() {
        return Selectors.SELF;
    }

    private static class Hook_困奋 implements Hook<StageStartEvent> {

        private final Skill skill;
        private final Hero user;
        private final Sanguosha game;

        public Hook_困奋(final Skill skill, final Hero user, final Sanguosha game) {
            this.skill = skill;
            this.user = user;
            this.game = game;
        }

        @Override
        public Class<StageStartEvent> getEventClass() {
            return StageStartEvent.class;
        }

        @Override
        public void before(final StageStartEvent event) {
            final Hero owner = event.getSource().getOwner();
            if (Objects.equals(owner, user)) {
                skill.action(event.getSource().getGame(), user, Collections.singletonList(user),
                        Collections.emptyList());
            }
        }

        @Override
        public void after(final StageStartEvent event) {
        }
    }

    private class Effect_困奋 extends AbsTargetingHeroEffect {

        public Effect_困奋(final Sanguosha game, final Hero user, final Collection<Card> resources,
                         final List<Hero> targets) {
            super(game, user.getPlayer(), 困奋.this, resources, targets);
        }

        @Override
        public void doEffect() {
            final Hero hero = user.getHero();
            hero.setHp(hero.getHp() - 1);
            final Collection<Card> cards = game.cardsStackPop(2);
            hero.getHand().gain(cards);
        }
    }
}
