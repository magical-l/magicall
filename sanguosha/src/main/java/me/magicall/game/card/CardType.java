package me.magicall.game.card;

import me.magicall.game.sanguosha.core.card.CardTypes;
import me.magicall.mark.Named;

import java.util.Objects;

/**
 * @author Liang Wenjian
 */
public interface CardType extends Named {
    /**
     * 本类型是否也是另一种类型。比如武器类型也是一种装备类型。
     *
     * @param other
     * @return
     */
    default boolean is(final CardType other) {
        for (CardType t = this; t != null; t = t.getParent()) {
            if (Objects.equals(t, other)) {
                return true;
            }
        }
        return false;
    }

    CardTypes getParent();
}
