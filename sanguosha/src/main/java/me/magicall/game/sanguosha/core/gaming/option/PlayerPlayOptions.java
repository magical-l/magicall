package me.magicall.game.sanguosha.core.gaming.option;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import me.magicall.game.card.Card;
import me.magicall.game.sanguosha.core.area.EquipArea;
import me.magicall.game.sanguosha.core.hero.Hero;
import me.magicall.game.sanguosha.core.skill.Skill;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * @author Liang Wenjian
 */
public class PlayerPlayOptions implements Options<SkillSelection> {

    private final ObjectMapper objectMapper;

    private final Hero hero;

    public PlayerPlayOptions(final Hero hero) {
        super();
        this.hero = hero;
        objectMapper = new ObjectMapper();
    }

    @Override
    public String getMsg() {
        return "请出牌。";
    }

    @Override
    public List<? extends Option> getOptions() {
        final List<Option> rt= Lists.newArrayList();
        //获取可以用的手牌
        hero.getHand().getCards().stream().forEach(card->{

        });
        //获取可以用的武器 TODO
        final EquipArea equip = hero.getEquip();
        final Card weapon = equip.getWeapon();
        //获取可以用的技能
        final Collection<Skill> skills = hero.getSkills();

        return rt;//TODO
    }

    @Override
    public SkillSelection toSelection(final String input) {
        try {
            final SkillSelection selection = objectMapper.readValue(input, SkillSelection.class);
            return selection;
        } catch (final IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
