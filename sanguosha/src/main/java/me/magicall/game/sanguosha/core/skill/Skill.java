package me.magicall.game.sanguosha.core.skill;

import me.magicall.game.Targetable;
import me.magicall.game.card.Card;
import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.gaming.target.Selector;
import me.magicall.game.sanguosha.core.unit.Hero;
import me.magicall.mark.Named;

import java.util.Collection;
import java.util.List;

/**
 * 技能。
 *
 * @author Liang Wenjian
 */
public interface Skill extends Named, Targetable {

    @Override
    default String getName() {
        return getClass().getSimpleName();
    }

    default void init(final Sanguosha game, final Hero owner) {
    }

    Effect<?> action(Sanguosha game, Hero user, List<Hero> targets, Collection<Card> resources);

    Selector getTargetSelector();

    default int getMinResourcesCount() {
        return 1;
    }

    default Integer getMaxResourcesCount() {
        return 1;
    }

    default boolean is主公技() {
        return false;
    }

    default boolean is锁定技() {
        return false;
    }
}
