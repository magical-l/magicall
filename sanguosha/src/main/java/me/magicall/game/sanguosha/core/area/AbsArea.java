package me.magicall.game.sanguosha.core.area;

import com.google.common.collect.Lists;
import me.magicall.game.card.Card;

import java.util.Collection;
import java.util.List;

/**
 * @author Liang Wenjian
 */
public abstract class AbsArea implements Area {

    private final String name;

    private final List<Card> cards;

    public AbsArea(final String name) {
        this(name, Lists.newArrayList());
    }

    protected AbsArea(final String name, final List<Card> cards) {
        this.name = name;
        this.cards = cards;
    }

    @Override
    public boolean canGain(final Collection<Card> cards) {
        return true;
    }

    @Override
    public List<Card> getCards() {
        return cards;
    }

    @Override
    public String getName() {
        return name;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder(getName()).append(":[");
        cards.stream().forEach(e -> sb.append(e).append(','));
        return sb.append(']').toString();
    }
}
