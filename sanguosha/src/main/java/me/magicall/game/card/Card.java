package me.magicall.game.card;

import me.magicall.game.Targetable;
import me.magicall.game.sanguosha.core.card.Flower;
import me.magicall.game.sanguosha.core.skill.Skill;
import me.magicall.mark.Named;

import java.util.Collection;

/**
 * @author Liang Wenjian
 */
public interface Card extends Named, Targetable {

    default boolean is(final CardType cardType) {
        return getType().is(cardType);
    }

    int getPoint();

    Flower getFlower();

    Collection<Skill> getSkills();

    CardType getType();

}
