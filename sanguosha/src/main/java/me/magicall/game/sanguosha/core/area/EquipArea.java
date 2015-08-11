package me.magicall.game.sanguosha.core.area;

import me.magicall.game.card.Card;
import me.magicall.game.sanguosha.core.card.CardTypes;

import java.util.Collection;

/**
 * @author Liang Wenjian
 */
public class EquipArea extends AbsArea {

    public EquipArea() {
        super("装备区");
    }

    @Override
    public boolean canGain(final Collection<Card> cards) {
        for (final Card card : cards) {
            if (!card.getType().is(CardTypes.EQUIP)) {
                return false;
            }
        }
        return true;
    }

    public Card getWeapon() {
        return getCards().get(0);
    }

    public Card getShield() {
        return getCards().get(1);
    }

    public Card getAttackHorse() {
        return getCards().get(2);
    }

    public Card getDefenceHorse() {
        return getCards().get(3);
    }
}
