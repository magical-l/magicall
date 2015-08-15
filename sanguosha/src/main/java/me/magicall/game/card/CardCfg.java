package me.magicall.game.card;

import com.google.common.collect.Lists;
import me.magicall.game.sanguosha.core.card.Flower;
import me.magicall.game.sanguosha.core.skill.Skill;

import java.util.Collection;
import java.util.Collections;

/**
 * 牌的配置，在游戏之外就已存在。
 *
 * @author Liang Wenjian
 */
public class CardCfg implements Card {

    private final String name;
    private final CardType type;

    //扑克牌属性
    private final int point;
    private final Flower flower;

    private final Collection<Skill> skills;

    public CardCfg(final CardType type, final int point, final Flower flower, final Skill skill) {
        this(type, point, flower, Collections.singleton(skill));
    }

    public CardCfg(final CardType type, final int point, final Flower flower, final Collection<Skill> skills) {
        name = getClass().getSimpleName();
        this.type = type;
        this.point = point;
        this.flower = flower;
        this.skills = Lists.newArrayList(skills);
    }

    public CardCfg(final String name, final CardType type, final int point, final Flower flower,
                   final Collection<Skill> skills) {
        this.name = name;
        this.type = type;
        this.point = point;
        this.flower = flower;
        this.skills = Lists.newArrayList(skills);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public CardType getType() {
        return type;
    }

    @Override
    public int getPoint() {
        return point;
    }

    @Override
    public Flower getFlower() {
        return flower;
    }

    @Override
    public Collection<Skill> getSkills() {
        return skills;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + hashCode() + ":{" +
                "name:'" + name + '\'' +
                ", type:" + type +
                ", point:" + point +
                ", flower:" + flower +
                ", skills:" + skills +
                '}';
    }
}
