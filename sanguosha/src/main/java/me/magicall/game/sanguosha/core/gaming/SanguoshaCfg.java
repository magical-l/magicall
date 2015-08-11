package me.magicall.game.sanguosha.core.gaming;

import me.magicall.game.card.CardCfg;
import me.magicall.game.sanguosha.core.hero.HeroCfg;
import me.magicall.game.sanguosha.core.player.Player;
import me.magicall.game.sanguosha.core.player.Role;

import java.util.Collection;
import java.util.Map;

/**
 * @author Liang Wenjian
 */
public class SanguoshaCfg implements GamingCfg {

    private final Collection<Player> players;
    private final Collection<HeroCfg> heroCfgs;
    private final Map<CardCfg, Integer> countOfCardCfg;
    private final boolean positionFixed;
    private final Map<Role, Integer> countOfRole;

    public SanguoshaCfg(final Collection<Player> players, final Collection<HeroCfg> heroCfgs,
                        final Map<CardCfg, Integer> countOfCardCfg, final boolean positionFixed,
                        final Map<Role, Integer> countOfRole) {
        super();
        this.players = players;
        this.heroCfgs = heroCfgs;
        this.countOfCardCfg = countOfCardCfg;
        this.positionFixed = positionFixed;
        this.countOfRole = countOfRole;
    }

    @Override
    public Collection<Player> getPlayers() {
        return players;
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
