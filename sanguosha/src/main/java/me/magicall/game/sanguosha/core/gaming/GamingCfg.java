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
public interface GamingCfg {

    Collection<Player> getPlayers();

    Collection<HeroCfg> getHeroCfgs();

    Map<CardCfg, Integer> getCountOfCardCfg();

    boolean isPositionFixed();

    Map<Role, Integer> getCountOfRole();
}
