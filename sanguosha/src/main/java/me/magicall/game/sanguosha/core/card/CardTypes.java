package me.magicall.game.sanguosha.core.card;

import me.magicall.game.card.CardType;

/**
 * @author Liang Wenjian
 */
public enum CardTypes implements CardType {
    BASE,
    JINNANG,
    IMMEDIATE_JINNANG(JINNANG),
    DELAYED_JINNANG(JINNANG),
    EQUIP,
    WEAPON(EQUIP),
    SHIELD(EQUIP),
    HORSE(EQUIP),
    ATTACK_HORSE(HORSE),
    DEFENSE_HORSE(HORSE);

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
