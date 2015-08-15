package me.magicall.game.sanguosha.core.skill;

import me.magicall.game.card.Card;
import me.magicall.game.card.Event;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.Collection;

/**
 * 一个技能的一次触发事件。
 *
 * @author Liang Wenjian
 */
public class SkillEvent extends Event {

    private static final long serialVersionUID = 5179146259740286366L;

    private Card resource;
    private Hero source;
    private Skill skill;
    private Collection<Hero> targets;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public SkillEvent(final Object source) {
        super(source);
    }

    public Card getResource() {
        return resource;
    }

    public void setResource(final Card resource) {
        this.resource = resource;
    }

    @Override
    public Hero getSource() {
        return source;
    }

    public void setSource(final Hero source) {
        this.source = source;
    }

    public Collection<Hero> getTargets() {
        return targets;
    }

    public void setTargets(final Collection<Hero> targets) {
        this.targets = targets;
    }

}
