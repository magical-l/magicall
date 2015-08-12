package me.magicall.game.sanguosha.core.gaming.stage;

import me.magicall.game.sanguosha.core.gaming.option.Selection;

import java.util.List;

/**
 * @author Liang Wenjian
 */
public class DiscardSelection implements Selection {

    private final List<Integer> cardIds;

    public DiscardSelection(final List<Integer> cardIds) {
        super();
        this.cardIds = cardIds;
    }

    public List<Integer> getCardIds() {
        return cardIds;
    }
}
