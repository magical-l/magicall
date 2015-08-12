package me.magicall.game.sanguosha.core.gaming.stage;

import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.unit.Hero;

/**
 * @author Liang Wenjian
 */
public abstract class AbsStage implements Stage {

    private final Sanguosha game;
    private final Hero hero;

    public AbsStage(final Sanguosha game, final Hero hero) {
        super();
        this.game = game;
        this.hero = hero;
    }

    @Override
    public void play() {
        final Sanguosha game = getGame();
        game.publishEvent(new StageStartEvent(this));

        playInternal();

        game.publishEvent(new StageEndEvent(this));
    }

    protected abstract void playInternal();

    @Override
    public Hero getOwner() {
        return hero;
    }

    @Override
    public Sanguosha getGame() {
        return game;
    }
}
