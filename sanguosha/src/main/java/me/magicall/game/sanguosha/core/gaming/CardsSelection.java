package me.magicall.game.sanguosha.core.gaming;

import me.magicall.game.sanguosha.core.gaming.option.Selection;

import java.util.List;

/**
 * @author Liang Wenjian
 */
public class CardsSelection implements Selection {

    private final List<Integer> cardIds;

    public CardsSelection(final List<Integer> cardIds) {
        super();
        this.cardIds = cardIds;
    }

    public List<Integer> getCardIds() {
        return cardIds;
    }
}
