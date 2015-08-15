package me.magicall.game.sanguosha.core.gaming.option;

import me.magicall.game.sanguosha.core.unit.HeroCfg;

import java.util.List;

/**
 * @author Liang Wenjian
 */
public class SelectHeroOptions implements Options<HeroSelection> {

    private final List<HeroCfg> options;

    public SelectHeroOptions(final List<HeroCfg> heroCfgs) {
        super();
        options = heroCfgs;
    }

    @Override
    public String getMsg() {
        return "请选择武将";
    }

    @Override
    public List<HeroCfg> getOptions() {
        return options;
    }

    @Override
    public HeroSelection toSelection(final String input) {
        final int selectedIndex = Integer.parseInt(input);
        final HeroCfg heroCfg = getOptions().get(selectedIndex);
        return new HeroSelection(heroCfg);
    }

    public static class SelectingHeroOption {

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
