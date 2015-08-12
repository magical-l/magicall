package me.magicall.game.card;

/**
 * 游戏中的一轮。
 *
 * @author Liang Wenjian
 */
public interface Round {

    void play();

    Game getGame();

    int getRoundIndex();
}
