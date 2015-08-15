package me.magicall.game.sanguosha.core.gaming.stage;

import me.magicall.game.sanguosha.core.gaming.CardsSelection;
import me.magicall.game.sanguosha.core.gaming.option.Options;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.List;

/**
 * @author Liang Wenjian
 */
public class DiscardOptions implements Options<CardsSelection> {

    private final Hero owner;
    private final int needDiscardCount;

    public DiscardOptions(final Hero owner, final int needDiscardCount) {
        super();
        this.owner = owner;
        this.needDiscardCount = needDiscardCount;
    }

    @Override
    public String getMsg() {
        return "需要弃置" + needDiscardCount + "张牌。";
    }

    @Override
    public List<?> getOptions() {
        return owner.getHand().getCards();
    }

    @Override
    public CardsSelection toSelection(final String input) {
        return null;//TODO
    }
}
