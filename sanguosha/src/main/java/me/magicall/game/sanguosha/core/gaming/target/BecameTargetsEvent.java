package me.magicall.game.sanguosha.core.gaming.target;

import me.magicall.game.card.Event;
import me.magicall.game.sanguosha.core.skill.Skill;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.Collection;

/**
 * @author Liang Wenjian
 */
public class BecameTargetsEvent extends Event {

    private static final long serialVersionUID = 8054436658248901302L;

    private Skill skill;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public BecameTargetsEvent(final Collection<Hero> source, final Skill skill) {
        super(source);
        this.skill = skill;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Hero> getSource() {
        return (Collection<Hero>) super.getSource();
    }

    public Skill getSkill() {
        return skill;
    }
}
