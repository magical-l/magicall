package me.magicall.game.sanguosha.core.gaming.round;

import com.google.common.collect.Lists;
import me.magicall.game.card.Round;
import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.List;

/**
 * 一轮。
 *
 * @author Liang Wenjian
 */
public class SanguoshaRound implements Round {

    private final Sanguosha game;
    private final int roundIndex;

    private final List<UnitTurn> unitTurns;
    private Hero curUnit;

    public SanguoshaRound(final Sanguosha game, final int roundIndex) {
        super();
        this.game = game;
        this.roundIndex = roundIndex;
        unitTurns = Lists.newArrayListWithExpectedSize(game.getSurvivors().size());
    }

    @Override
    public void play() {
        game.publishEvent(new RoundStartEvent(this));
        game.getSurvivors().stream().forEach(player -> {
            final UnitTurn unitTurn = new HeroTurn(this, player.getHero());
            unitTurn.play();
            unitTurns.add(unitTurn);
        });
        game.publishEvent(new RoundEndEvent(this));
    }

    @Override
    public Sanguosha getGame() {
        return game;
    }

    @Override
    public int getRoundIndex() {
        return roundIndex;
    }

    public void setCurUnit(final Hero curUnit) {
        this.curUnit = curUnit;
    }

    public Hero getCurUnit() {
        return curUnit;
    }
}
