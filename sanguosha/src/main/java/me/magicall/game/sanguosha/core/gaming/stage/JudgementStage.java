package me.magicall.game.sanguosha.core.gaming.stage;

import me.magicall.game.card.Card;
import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.List;
import java.util.ListIterator;

/**
 * 判定阶段。
 * 判定阶段：角色须按置入判定区里的倒序进行其判定区里的延时类锦囊牌的使用结算，即先结算最后置入判定区里的延时类锦囊牌。
 *
 * @author Liang Wenjian
 */
public class JudgementStage extends AbsStage {

    public JudgementStage(final Sanguosha game, final Hero hero) {
        super(game, hero);
    }

    @Override
    protected void playInternal() {
        final Sanguosha game = getGame();
        final Hero owner = getOwner();
        final List<Card> cards = owner.getJudgement().getCards();
        for (final ListIterator<Card> iterator = cards.listIterator(); iterator.hasPrevious(); ) {
            final Card card = iterator.previous();
            game.cardWork(card, owner);
        }
    }
}
