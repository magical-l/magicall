package me.magicall.game.sanguosha.core.skill;

import me.magicall.game.card.Card;
import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.player.GamingPlayer;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.Collection;
import java.util.List;

/**
 * @author Liang Wenjian
 */
public abstract class AbsTargetingHeroEffect extends AbsEffect<Hero> {

    public AbsTargetingHeroEffect(final Sanguosha game, final GamingPlayer user, final Skill skill,
                                  final Collection<Card> resources, final List<Hero> targets) {
        super(game, user, skill, resources, targets);
    }
}
