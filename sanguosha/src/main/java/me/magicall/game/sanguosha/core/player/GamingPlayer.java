package me.magicall.game.sanguosha.core.player;

import me.magicall.game.sanguosha.core.gaming.Position;
import me.magicall.game.sanguosha.core.unit.Hero;

/**
 * 游戏中的玩家。
 *
 * @author Liang Wenjian
 */
public class GamingPlayer {

    private Position position;
    /**
     * 身份
     */
    private Role role;
    /**
     * 武将
     */
    private Hero hero;
    /**
     * 玩家代理
     */
    private Player player;//player在游戏中有可能会被替换成AI。
    /**
     * 是否已阵亡
     */
    private boolean dead;

    public Role getRole() {
        return role;
    }

    public void setRole(final Role role) {
        this.role = role;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(final Hero hero) {
        this.hero = hero;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(final Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + hashCode() + ":{" +
                "role:" + role +
                ", hero:" + hero +
                ", player:" + player +
                ", dead:" + dead +
                '}';
    }
}
