package me.magicall.game.sanguosha.core.gaming.stage;

import me.magicall.game.card.Event;
import me.magicall.game.sanguosha.core.gaming.round.HeroTurn;

import java.util.Iterator;

/**
 * @author Liang Wenjian
 */
public class AfterStagesEvent extends Event {

    private static final long serialVersionUID = 2465699218424956984L;

    private final Stage curStage;
    private Iterator<Stage> stageIterator;

    public AfterStagesEvent(final HeroTurn source, final Stage curStage, final Iterator<Stage> iterator) {
        super(source);
        this.curStage = curStage;
        stageIterator = iterator;
    }

    public Stage getCurStage() {
        return curStage;
    }

    public Iterator<Stage> getStageIterator() {
        return stageIterator;
    }
}
