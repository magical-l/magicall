package me.magicall.game.sanguosha.core.skill;

/**
 * @author Liang Wenjian
 */
public abstract class SkillCfg implements Skill {

    private final String name;

    public SkillCfg(final String name) {
        super();
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
