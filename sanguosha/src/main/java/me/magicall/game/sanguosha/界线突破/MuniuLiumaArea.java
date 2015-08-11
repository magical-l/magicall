package me.magicall.game.sanguosha.界线突破;

import me.magicall.game.sanguosha.core.area.AbsArea;
import me.magicall.game.card.Card;

import java.util.Collection;

/**
 * 木牛流马牌区。
 *
 * @author Liang Wenjian
 */
public class MuniuLiumaArea extends AbsArea {
    public MuniuLiumaArea() {
        super("木牛流马");
    }

    @Override
    public boolean canGain(final Collection<Card> cards) {
        return cards.size() == 1;
    }
}
