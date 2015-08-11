package me.magicall.game.sanguosha.core.hero.base;

import me.magicall.game.Targetable;
import me.magicall.mark.Named;

/**
 * @author Liang Wenjian
 */
public interface Unit extends Named, Targetable {

    default int getHpUpperBound() {
        return getCfg().getHpUpperBound();
    }

    UnitCfg getCfg();
}
