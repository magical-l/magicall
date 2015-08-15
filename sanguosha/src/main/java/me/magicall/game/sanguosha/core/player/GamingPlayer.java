package me.magicall.game.sanguosha.core.player;

import me.magicall.game.sanguosha.core.gaming.position.Position;
import me.magicall.game.sanguosha.core.gaming.option.Options;
import me.magicall.game.sanguosha.core.gaming.option.Selection;
import me.magicall.game.sanguosha.core.unit.Hero;

/**
 * 游戏中的玩家。
 *
 * @author Liang Wenjian
 */
public class GamingPlayer implements Player {

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
    private IO io;//player在游戏中有可能会被替换成AI。
    /**
     * 是否已阵亡
     */
    private boolean dead;

    @Override
    public <T extends Selection> T requireInput(final Options<T> options) {
        return io.requireInput(options);
    }

    @Override
    public void output(final Object o) {
        io.output(o);
    }

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

    public IO getIO() {
        return io;
    }

    public void setIO(final IO io) {
        this.io = io;
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
                ", io:" + io +
                ", dead:" + dead +
                '}';
    }
}
