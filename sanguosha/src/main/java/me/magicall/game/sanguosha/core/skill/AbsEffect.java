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
public abstract class AbsEffect<TARGET_TYPE extends Targetable> implements Effect<TARGET_TYPE> {

    protected final Sanguosha game;
    protected final Skill skill;
    protected GamingPlayer user;
    protected Collection<Card> resources;
    protected List<TARGET_TYPE> targets;

    public AbsEffect(final Sanguosha game, final GamingPlayer user, final Skill skill, final Collection<Card> resources,
                     final List<TARGET_TYPE> targets) {
        super();
        this.game = game;
        this.user = user;
        this.skill = skill;
        this.resources = resources;
        this.targets = targets;
    }

    @Override
    public Sanguosha getGame() {
        return game;
    }

    @Override
    public Skill getSkill() {
        return skill;
    }

    @Override
    public GamingPlayer getUser() {
        return user;
    }

    @Override
    public Collection<Card> getResources() {
        return resources;
    }

    @Override
    public List<TARGET_TYPE> getTargets() {
        return targets;
    }
}
