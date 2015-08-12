package me.magicall.game.sanguosha.core.gaming.stage;

import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.unit.Hero;

/**
 * 准备阶段。
 * 准备阶段：暂时没有作用。
 *
 * @author Liang Wenjian
 */
public class PrepareStage extends AbsStage {

    public PrepareStage(final Sanguosha game, final Hero hero) {
        super(game, hero);
    }

    @Override
    protected void playInternal() {
    }

}
