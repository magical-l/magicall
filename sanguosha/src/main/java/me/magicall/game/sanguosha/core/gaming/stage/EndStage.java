package me.magicall.game.sanguosha.core.gaming.stage;

import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.unit.Hero;

/**
 * 结束阶段。
 * 结束阶段：暂时没有作用。
 *
 * @author Liang Wenjian
 */
public class EndStage extends AbsStage {

    public EndStage(final Sanguosha game, final Hero hero) {
        super(game, hero);
    }

    @Override
    protected void playInternal() {
    }
}
