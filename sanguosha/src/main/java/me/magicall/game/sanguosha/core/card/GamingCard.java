package me.magicall.game.sanguosha.core.card;

import me.magicall.game.card.Card;
import me.magicall.game.card.CardCfg;
import me.magicall.game.card.CardType;
import me.magicall.game.sanguosha.core.skill.Skill;
import me.magicall.game.sanguosha.core.unit.Hero;
import me.magicall.mark.HasIdGetter.HasIntIdGetter;

import java.util.Collection;
import java.util.Objects;

/**
 * 游戏中的牌。同一个花色+点数+名字可能也会有多张。
 *
 * @author Liang Wenjian
 */
public class GamingCard implements HasIntIdGetter, Card {
    private final Integer id;

    private final CardCfg cardCfg;

    private Hero owner;

    public GamingCard(final Integer id, final CardCfg cardCfg) {
        super();
        this.id = id;
        this.cardCfg = cardCfg;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return cardCfg.getName();
    }

    @Override
    public CardType getType() {
        return cardCfg.getType();
    }

    @Override
    public int getPoint() {
        return cardCfg.getPoint();
    }

    @Override
    public Flower getFlower() {
        return cardCfg.getFlower();
    }

    @Override
    public Collection<Skill> getSkills() {
        return cardCfg.getSkills();
    }

    public Hero getOwner() {
        return owner;
    }

    public void setOwner(final Hero owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GamingCard)) {
            return false;
        }
        final GamingCard card = (GamingCard) o;
        return Objects.equals(getId(), card.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + hashCode() + ":{" +
                "id:" + id +
                ", cardCfg:" + cardCfg +
                ", name:'" + getName() + '\'' +
                ", type:" + getType() +
                ", point:" + getPoint() +
                ", flower:" + getFlower() +
                ", skills:" + getSkills() +
                '}';
    }
}
