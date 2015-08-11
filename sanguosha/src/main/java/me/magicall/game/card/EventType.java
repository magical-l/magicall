package me.magicall.game.card;

import me.magicall.mark.HasIdGetter.HasStrIdGetter;
import me.magicall.mark.Named;

/**
 * @author Liang Wenjian
 */
@FunctionalInterface
public interface EventType extends Named, HasStrIdGetter {
    @Override
    default String getName() {
        return getId();
    }
}
