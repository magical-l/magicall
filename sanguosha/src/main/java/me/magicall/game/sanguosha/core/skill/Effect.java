package me.magicall.game.sanguosha.core.skill;

import me.magicall.game.Targetable;
import me.magicall.game.card.Card;
import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.player.GamingPlayer;

import java.util.Collection;
import java.util.List;

/**
 * @author Liang Wenjian
 */
public interface Effect<TARGET_TYPE extends Targetable> {

    Sanguosha getGame();

    Skill getSkill();

    GamingPlayer getUser();

    Collection<Card> getResources();

    List<TARGET_TYPE> getTargets();

    void doEffect();
}
