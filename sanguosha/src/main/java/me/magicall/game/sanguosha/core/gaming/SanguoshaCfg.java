package me.magicall.game.sanguosha.core.gaming;

import me.magicall.game.card.CardCfg;
import me.magicall.game.sanguosha.core.unit.HeroCfg;
import me.magicall.game.sanguosha.core.player.IO;
import me.magicall.game.sanguosha.core.player.Role;

import java.util.Collection;
import java.util.Map;

/**
 * @author Liang Wenjian
 */
public class SanguoshaCfg implements GamingCfg {

    private final Collection<IO> IOs;
    private final Collection<HeroCfg> heroCfgs;
    private final Map<CardCfg, Integer> countOfCardCfg;
    private final boolean positionFixed;
    private final Map<Role, Integer> countOfRole;

    public SanguoshaCfg(final Collection<IO> IOs, final Collection<HeroCfg> heroCfgs,
                        final Map<CardCfg, Integer> countOfCardCfg, final boolean positionFixed,
                        final Map<Role, Integer> countOfRole) {
        super();
        this.IOs = IOs;
        this.heroCfgs = heroCfgs;
        this.countOfCardCfg = countOfCardCfg;
        this.positionFixed = positionFixed;
        this.countOfRole = countOfRole;
    }

    @Override
    public Collection<IO> getIOs() {
        return IOs;
    }

    @Override
    public Collection<HeroCfg> getHeroCfgs() {
        return heroCfgs;
    }

    @Override
    public Map<CardCfg, Integer> getCountOfCardCfg() {
        return countOfCardCfg;
    }

    @Override
    public boolean isPositionFixed() {
        return positionFixed;
    }

    @Override
    public Map<Role, Integer> getCountOfRole() {
        return countOfRole;
    }
}
