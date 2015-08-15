package me.magicall.game.sanguosha.core.unit;

import com.google.common.collect.Lists;
import me.magicall.game.card.UnitCfg;
import me.magicall.game.sanguosha.core.skill.Skill;

import java.util.Collection;

/**
 * 武将的初始设置。
 *
 * @author Liang Wenjian
 */
public class HeroCfg implements UnitCfg {

    private final String name;
    private final int hpUpperBound;
    private final Country country;
    private final Gender gender;
    private final Collection<Skill> skills;

    public HeroCfg(final String name, final int hpUpperBound, final Country country, final Gender gender,
                   final Skill... skills) {
        this(name, hpUpperBound, country, gender, Lists.newArrayList(skills));
    }

    public HeroCfg(final String name, final int hpUpperBound, final Country country, final Gender gender,
                   final Collection<Skill> skills) {
        super();
        this.name = name;
        this.hpUpperBound = hpUpperBound;
        this.country = country;
        this.gender = gender;
        this.skills = skills;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHpUpperBound() {
        return hpUpperBound;
    }

    public Country getCountry() {
        return country;
    }

    public Gender getGender() {
        return gender;
    }

    public Collection<Skill> getSkills() {
        return skills;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + hashCode() + ":{" +
                "name:'" + name + '\'' +
                ", hpUpperBound:" + hpUpperBound +
                ", country:" + country +
                ", gender:" + gender +
                ", skills:" + skills +
                '}';
    }
}
