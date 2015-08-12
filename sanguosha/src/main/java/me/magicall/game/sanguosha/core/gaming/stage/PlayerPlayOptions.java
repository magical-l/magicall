package me.magicall.game.sanguosha.core.gaming.stage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import me.magicall.game.sanguosha.core.gaming.GetUsableCardsEvent;
import me.magicall.game.sanguosha.core.gaming.GetUsableSkillsEvent;
import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.gaming.option.Option;
import me.magicall.game.sanguosha.core.gaming.option.Options;
import me.magicall.game.sanguosha.core.gaming.option.SkillSelection;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.io.IOException;
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
        final Sanguosha game = hero.getGame();
        final List<Option> rt = Lists.newArrayList();
        //获取可以用的手牌
        final GetUsableCardsEvent getUsableCardsEvent = new GetUsableCardsEvent(this,
                Lists.newArrayList(hero.getHand().getCards()));
        game.publishEvent(getUsableCardsEvent);
        getUsableCardsEvent.getCards().forEach(card -> rt.add(new OptionImpl(card)));
        //获取可以用的技能
        final GetUsableSkillsEvent getUsableSkillsEvent = new GetUsableSkillsEvent(this,
                Lists.newArrayList(hero.getSkills()));
        game.publishEvent(getUsableSkillsEvent);
        getUsableSkillsEvent.getSkills().forEach(e -> rt.add(new OptionImpl(e)));
        //结束按钮
        rt.add(new OptionImpl("结束"));
        return rt;
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

    public static class OptionImpl implements Option {
        private final Object o;

        public OptionImpl(final Object o) {
            this.o = o;
        }
    }
}
