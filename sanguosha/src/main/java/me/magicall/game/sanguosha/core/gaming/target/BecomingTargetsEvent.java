package me.magicall.game.sanguosha.core.gaming.target;

import me.magicall.game.card.Event;
import me.magicall.game.sanguosha.core.skill.Skill;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.List;

/**
 * @author Liang Wenjian
 */
public class BecomingTargetsEvent extends Event {

    private static final long serialVersionUID = 6740619290789649296L;

    private Skill skill;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public BecomingTargetsEvent(final List<Hero> source, final Skill skill) {
        super(source);
        this.skill = skill;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Hero> getSource() {
        return (List<Hero>) source;
    }

    public Skill getSkill() {
        return skill;
    }
}
