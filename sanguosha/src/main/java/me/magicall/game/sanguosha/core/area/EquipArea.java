package me.magicall.game.sanguosha.core.area;

import com.google.common.collect.Lists;
import me.magicall.game.card.Card;
import me.magicall.game.sanguosha.core.card.CardTypes;
import me.magicall.game.sanguosha.core.gaming.GameException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Liang Wenjian
 */
public class EquipArea extends AbsArea {

    public static final int WEAPON_INDEX = 0;
    public static final int SHIELD_INDEX = 1;
    public static final int ATTACK_HORSE_INDEX = 2;
    public static final int DEFENCE_HORSE_INDEX = 3;

    public EquipArea() {
        super("装备区", Lists.newArrayList(null, null, null, null));
    }

    @Override
    public List<Card> getCards() {
        return super.getCards().stream().filter(e -> e != null).collect(Collectors.toList());
    }

    @Override
    public boolean canGain(final Collection<Card> cards) {
        for (final Card card : cards) {
            if (!card.getType().is(CardTypes.装备)) {
                return false;
            }
        }
        return true;
    }

    public void setWeapon(final Card weapon) {
        if (!weapon.getType().is(CardTypes.武器)) {
            throw new GameException();
        }
        getCards().set(WEAPON_INDEX, weapon);
    }

    public Card getWeapon() {
        return getCards().get(WEAPON_INDEX);
    }

    public void setShield(final Card shield) {
        if (!shield.getType().is(CardTypes.防具)) {
            throw new GameException();
        }
        getCards().set(SHIELD_INDEX, shield);
    }

    public Card getShield() {
        return getCards().get(SHIELD_INDEX);
    }

    public void setAttackHorse(final Card attackHorse) {
        if (!attackHorse.getType().is(CardTypes.进攻马)) {
            throw new GameException();
        }
        getCards().set(ATTACK_HORSE_INDEX, attackHorse);
    }

    public Card getAttackHorse() {
        return getCards().get(ATTACK_HORSE_INDEX);
    }

    public void setDefenceHorse(final Card defenceHorse) {
        if (!defenceHorse.getType().is(CardTypes.进攻马)) {
            throw new GameException();
        }
        getCards().set(DEFENCE_HORSE_INDEX, defenceHorse);
    }

    public Card getDefenceHorse() {
        return getCards().get(DEFENCE_HORSE_INDEX);
    }
}
