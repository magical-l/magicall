package me.magicall.game.sanguosha.core.gaming.option;

import java.util.List;

/**
 * @author Liang Wenjian
 */
public class SkillSelection implements Selection {
    private final Integer skillId;
    private final List<Integer> targetIds;
    private final List<Integer> resourceIds;

    public SkillSelection(final Integer skillId, final List<Integer> targetIds, final List<Integer> resourceIds) {
        this.skillId = skillId;
        this.targetIds = targetIds;
        this.resourceIds = resourceIds;
    }

    public Integer getSkillId() {
        return skillId;
    }

    public List<Integer> getTargetIds() {
        return targetIds;
    }

    public List<Integer> getResourceIds() {
        return resourceIds;
    }
}
