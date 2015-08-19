package me.magicall.game.sanguosha.core.card;

import me.magicall.game.card.CardType;

/**
 * @author Liang Wenjian
 */
public enum CardTypes implements CardType {
    基本牌,
    锦囊,
    非延时锦囊(锦囊),
    延时锦囊(锦囊),
    装备,
    武器(装备),
    防具(装备),
    马(装备),
    进攻马(马),
    防御马(马);

    private final CardTypes parent;

    CardTypes(final CardTypes parent) {
        this.parent = parent;
    }

    CardTypes() {
        this(null);
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public CardTypes getParent() {
        return parent;
    }
}
