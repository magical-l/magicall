package me.magicall.game.sanguosha.core.gaming.round;

import com.google.common.collect.Lists;
import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.gaming.stage.AfterStagesEvent;
import me.magicall.game.sanguosha.core.gaming.stage.DiscardStage;
import me.magicall.game.sanguosha.core.gaming.stage.EndStage;
import me.magicall.game.sanguosha.core.gaming.stage.GainCardStage;
import me.magicall.game.sanguosha.core.gaming.stage.JudgementStage;
import me.magicall.game.sanguosha.core.gaming.stage.PlayerPlayStage;
import me.magicall.game.sanguosha.core.gaming.stage.PrepareStage;
import me.magicall.game.sanguosha.core.gaming.stage.Stage;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.Iterator;
import java.util.List;

/**
 * 一个武将的一个回合。
 *
 * @author Liang Wenjian
 */
public class HeroTurn implements UnitTurn {

    private final SanguoshaRound round;
    private final Hero owner;

    public HeroTurn(final SanguoshaRound round, final Hero owner) {
        super();
        this.round = round;
        this.owner = owner;
    }

    @Override
    public void play() {
        final Sanguosha game = getGame();
        game.publishEvent(new TurnStartEvent(this));

        round.setCurUnit(getOwner());

        final List<Stage> stages = Lists.newArrayList(//
                new PrepareStage(game, owner),//
                new JudgementStage(game, owner),//
                new GainCardStage(game, owner),//
                new PlayerPlayStage(game, owner),//
                new DiscardStage(game, owner),//
                new EndStage(game, owner));

        for (Iterator<Stage> iterator = stages.iterator(); iterator.hasNext(); ) {
            final Stage stage = iterator.next();
            stage.play();
            final AfterStagesEvent event = new AfterStagesEvent(this, stage, iterator);
            game.publishEvent(event);
            iterator = event.getStageIterator();
        }

        round.setCurUnit(null);

        game.publishEvent(new TurnEndEvent(this));
    }

    @Override
    public Hero getOwner() {
        return owner;
    }

    public Sanguosha getGame() {
        return round.getGame();
    }
}
