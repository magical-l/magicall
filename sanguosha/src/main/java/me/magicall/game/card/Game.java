package me.magicall.game.card;

import me.magicall.game.sanguosha.core.player.GamingPlayer;

import java.util.List;

/**
 * @author Liang Wenjian
 */
public interface Game {

    void play();

    List<? extends GamingPlayer> getSurvivors();

    List<GamingPlayer> getPlayers();
}
