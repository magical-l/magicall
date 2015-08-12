package me.magicall.game.sanguosha.core.gaming.option;

import me.magicall.game.sanguosha.core.unit.HeroCfg;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Liang Wenjian
 */
public class SelectHeroOptions implements Options<HeroSelection> {

    private final List<SelectingHeroOption> options;

    public SelectHeroOptions(final List<HeroCfg> heroCfgs) {
        super();
        options = heroCfgs.stream().map(SelectingHeroOption::new).collect(Collectors.toList());
    }

    @Override
    public String getMsg() {
        return "请选择武将";
    }

    @Override
    public List<SelectingHeroOption> getOptions() {
        return options;
    }

    @Override
    public HeroSelection toSelection(final String input) {
        final int selectedIndex = Integer.parseInt(input);
        final SelectingHeroOption option = getOptions().get(selectedIndex);
        return new HeroSelection(option.getHeroCfg());
    }

    public static class SelectingHeroOption implements Option {

        private final HeroCfg heroCfg;

        public SelectingHeroOption(final HeroCfg heroCfg) {
            super();
            this.heroCfg = heroCfg;
        }

        public HeroCfg getHeroCfg() {
            return heroCfg;
        }

        public String toString() {
            return "{hero:" + heroCfg.getName() + '}';
        }
    }
}
