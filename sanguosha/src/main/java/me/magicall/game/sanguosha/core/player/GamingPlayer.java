package me.magicall.game.sanguosha.core.player;

import me.magicall.game.sanguosha.core.hero.Hero;

import java.util.Collection;

/**
 * 游戏中的玩家。
 *
 * @author Liang Wenjian
 */
public class GamingPlayer {

    private Role role;

    private Collection<Hero> heros;
    private Player player;//player在游戏中有可能会被替换成AI。

    private boolean dead;

    public Role getRole() {
        return role;
    }

    public void setRole(final Role role) {
        this.role = role;
    }

    public Collection<Hero> getHeros() {
        return heros;
    }

    public void setHeros(final Collection<Hero> heros) {
        this.heros = heros;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(final boolean dead) {
        this.dead = dead;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + hashCode() + ":{" +
                "role:" + role +
                ", heros:" + heros +
                ", player:" + player +
                ", dead:" + dead +
                '}';
    }
}
