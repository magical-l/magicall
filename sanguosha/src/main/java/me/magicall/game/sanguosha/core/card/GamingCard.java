package me.magicall.game.sanguosha.core.card;

import me.magicall.game.card.Card;
import me.magicall.game.card.CardCfg;
import me.magicall.game.card.CardType;
import me.magicall.game.sanguosha.core.skill.Skill;
import me.magicall.mark.HasIdGetter.HasIntIdGetter;

import java.util.Collection;

/**
 * 游戏中的牌。同一个花色+点数+名字可能也会有多张。
 *
 * @author Liang Wenjian
 */
public class GamingCard implements HasIntIdGetter, Card {
    private final Integer id;

    private final CardCfg cardCfg;

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
