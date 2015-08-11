package me.magicall.game.sanguosha.core.card;

import me.magicall.game.card.Card;
import me.magicall.game.card.CardCfg;

import java.util.Collection;
import java.util.Collections;

/**
 * 转化的牌。比如【龙胆】、【奇策】等技能，用一张或多张牌当成另外一张牌使用。
 *
 * @author Liang Wenjian
 */
public class TransCard extends GamingCard {

    private final Collection<Card> rawCards;

    public TransCard(final CardCfg cardCfg, final Card rawCard) {
        this(cardCfg, Collections.singleton(rawCard));
    }

    public TransCard(final CardCfg cardCfg, final Collection<Card> rawCards) {
        super(null, cardCfg);
        this.rawCards = rawCards;
    }

    public Collection<Card> getRawCards() {
        return rawCards;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + hashCode() + ":{" + super.toString() +
                ", rawCards:" + rawCards +
                '}';
    }
}
