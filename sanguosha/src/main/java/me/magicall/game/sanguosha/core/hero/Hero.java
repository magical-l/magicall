package me.magicall.game.sanguosha.core.hero;

import com.google.common.collect.Lists;
import me.magicall.game.sanguosha.core.area.EquipArea;
import me.magicall.game.sanguosha.core.area.HandArea;
import me.magicall.game.sanguosha.core.area.JudgementArea;
import me.magicall.game.sanguosha.core.gaming.Position;
import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.hero.base.Unit;
import me.magicall.game.sanguosha.core.player.GamingPlayer;
import me.magicall.game.sanguosha.core.skill.Skill;

import java.util.Collection;

/**
 * 武将。
 *
 * @author Liang Wenjian
 */
public class Hero implements Unit<HeroCfg> {

    private final HeroCfg heroCfg;

    //游戏进行时数据
    private final Sanguosha game;
    private GamingPlayer player;
    private Position coordinate;
    private int hp;

    private final HandArea hand = new HandArea();
    private final EquipArea equip = new EquipArea();
    private final JudgementArea judgement = new JudgementArea();

    //下面这些初始化数据在游戏中是可以改变的。
    private String name;
    private int hpUpperBound;
    private Country country;
    private Collection<Skill> skills;

    public Hero(final HeroCfg heroCfg, final Sanguosha game, final GamingPlayer player, final Position coordinate) {
        this.heroCfg = heroCfg;
        this.game = game;
        this.player = player;
        this.coordinate = coordinate;
        name = heroCfg.getName();
        hpUpperBound = heroCfg.getHpUpperBound();
        country = heroCfg.getCountry();
        skills = Lists.newArrayList(heroCfg.getSkills());
        hp = heroCfg.getHpUpperBound();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(final int hp) {
        this.hp = hp;
    }

    @Override
    public int getHpUpperBound() {
        return hpUpperBound;
    }

    @Override
    public HeroCfg getCfg() {
        return heroCfg;
    }

    public void setHpUpperBound(final int hpUpperBound) {
        this.hpUpperBound = hpUpperBound;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(final Country country) {
        this.country = country;
    }

    public Collection<Skill> getSkills() {
        return skills;
    }

    public void setSkills(final Collection<Skill> skills) {
        this.skills = skills;
    }

    public Position getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(final Position coordinate) {
        this.coordinate = coordinate;
    }

    public Sanguosha getGame() {
        return game;
    }

    public GamingPlayer getPlayer() {
        return player;
    }

    public void setPlayer(final GamingPlayer player) {
        this.player = player;
    }

    public HandArea getHand() {
        return hand;
    }

    public EquipArea getEquip() {
        return equip;
    }

    public JudgementArea getJudgement() {
        return judgement;
    }
}
