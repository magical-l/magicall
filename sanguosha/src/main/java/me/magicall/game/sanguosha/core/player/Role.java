package me.magicall.game.sanguosha.core.player;

import me.magicall.mark.Named;

/**
 * 身份。
 *
 * @author Liang Wenjian
 */
public enum Role implements Named {
    主公,
    忠臣,
    反贼,
    内奸;

    @Override
    public String getName() {
        return name();
    }
}
