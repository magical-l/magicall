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
public interface GamingCfg {

    Collection<IO> getIOs();

    Collection<HeroCfg> getHeroCfgs();

    Map<CardCfg, Integer> getCountOfCardCfg();

    boolean isPositionFixed();

    Map<Role, Integer> getCountOfRole();
}
