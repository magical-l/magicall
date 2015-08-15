package me.magicall.game.sanguosha.core.gaming;

import me.magicall.game.sanguosha.core.gaming.option.Selection;

/**
 * @author Liang Wenjian
 */
public class CardSelection implements Selection {

    private final Integer cardId;

    public CardSelection(final Integer cardId) {
        super();
        this.cardId = cardId;
    }

    public Integer getCardId() {
        return cardId;
    }
}
