package me.magicall.game.sanguosha.core.area;

import com.google.common.collect.Lists;
import me.magicall.game.card.Card;

import java.util.Collection;
import java.util.Queue;

/**
 * @author Liang Wenjian
 */
public class CardStack extends AbsArea {
    public CardStack() {
        super("牌堆", Lists.newLinkedList());
    }

    public Collection<Card> pop(final int count) {
        final Queue<Card> cards = (Queue<Card>) getCards();
        final Collection<Card> rt = Lists.newArrayList();
        for (int i = 0; i < count; i++) {
            final Card remove = cards.remove();
            rt.add(remove);
        }
        return rt;
    }
}
