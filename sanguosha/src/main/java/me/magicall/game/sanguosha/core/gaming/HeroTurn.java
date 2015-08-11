package me.magicall.game.sanguosha.core.gaming;

import com.google.common.collect.Lists;
import me.magicall.game.sanguosha.core.gaming.stage.DiscardStage;
import me.magicall.game.sanguosha.core.gaming.stage.EndStage;
import me.magicall.game.sanguosha.core.gaming.stage.GainCardStage;
import me.magicall.game.sanguosha.core.gaming.stage.JudgementStage;
import me.magicall.game.sanguosha.core.gaming.stage.PlayerPlayStage;
import me.magicall.game.sanguosha.core.gaming.stage.Stage;
import me.magicall.game.sanguosha.core.gaming.stage.StartStage;
import me.magicall.game.sanguosha.core.hero.Hero;

import java.util.List;

/**
 * 一个武将的一个回合。
 *
 * @author Liang Wenjian
 */
public class HeroTurn implements UnitTurn{

    private final Sanguosha game;
    private final Hero owner;

    public HeroTurn(final Sanguosha game, final Hero owner) {
        super();
        this.game = game;
        this.owner = owner;
    }

    @Override
    public void play() {
        final List<Stage> stages = Lists.newArrayList();
        stages.add(new StartStage(game, owner));
        stages.add(new JudgementStage(game, owner));
        stages.add(new GainCardStage(game, owner));
        stages.add(new PlayerPlayStage(game, owner));
        stages.add(new DiscardStage(game, owner));
        stages.add(new EndStage(game, owner));
        stages.stream().forEach(Stage::play);
    }

    @Override
    public Hero getOwner() {
        return owner;
    }

    public Sanguosha getGame() {
        return game;
    }
}
