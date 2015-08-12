package me.magicall.game.sanguosha.core.gaming.stage;

import java.util.List;

/**
 * @author Liang Wenjian
 */
public class PlaySelection {

    private List<Integer> cardIds;
    private Integer skillId;
    private List<Integer> targetPositions;

    public PlaySelection() {
        super();
    }

    public PlaySelection(final List<Integer> cardIds, final Integer skillId,
                         final List<Integer> targetPositions) {
        this.cardIds = cardIds;
        this.skillId = skillId;
        this.targetPositions = targetPositions;
    }

    public List<Integer> getCardIds() {
        return cardIds;
    }

    public void setCardIds(final List<Integer> cardIds) {
        this.cardIds = cardIds;
    }

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(final Integer skillId) {
        this.skillId = skillId;
    }

    public List<Integer> getTargetPositions() {
        return targetPositions;
    }

    public void setTargetPositions(final List<Integer> targetPositions) {
        this.targetPositions = targetPositions;
    }
}
