package me.magicall.game.sanguosha.core.gaming.stage;

import me.magicall.game.card.Card;
import me.magicall.game.sanguosha.core.area.Area;
import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.hero.Hero;

import java.util.List;
import java.util.ListIterator;

/**
 * @author Liang Wenjian
 */
public class JudgementStage extends AbsStage{

    public JudgementStage(final Sanguosha game, final Hero hero) {
        super(game, hero);
    }

    @Override
    protected void playInternal() {
        final Sanguosha game = getGame();
        final Hero owner = getOwner();
        final Area judgementArea = owner.getJudgement();
        final List<Card> cards = judgementArea.getCards();
        for (final ListIterator<Card> iterator=cards.listIterator(); iterator.hasPrevious(); ) {
            final Card card = iterator.previous();
            game.cardWork(card);
        }
    }
}
