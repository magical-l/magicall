package me.magicall.game.sanguosha.core.gaming;

import me.magicall.game.card.Event;
import me.magicall.game.sanguosha.core.skill.Skill;

import java.util.List;

/**
 * @author Liang Wenjian
 */
public class GetUsableSkillsEvent extends Event {

    private static final long serialVersionUID = -4253301983361808395L;

    private final List<Skill> skills;

    public GetUsableSkillsEvent(final Object source, final List<Skill> skills) {
        super(source);
        this.skills = skills;
    }

    public List<Skill> getSkills() {
        return skills;
    }
}
