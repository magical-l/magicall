package me.magicall.game.sanguosha.core.gaming.stage;

import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.gaming.option.Options;
import me.magicall.game.sanguosha.core.gaming.option.Selection;
import me.magicall.game.sanguosha.core.hero.Hero;
import me.magicall.game.sanguosha.core.player.GamingPlayer;
import me.magicall.game.sanguosha.core.player.Player;

/**
 * @author Liang Wenjian
 */
public class StartStage extends AbsStage {

    public StartStage(final Sanguosha game, final Hero hero) {
        super(game, hero);
    }

    @Override
    protected void playInternal() {
        final Hero owner = getOwner();
        final GamingPlayer gamingPlayer = owner.getPlayer();
        final Player player = gamingPlayer.getPlayer();
        //TODO
        final Options<Selection> options = null;
        player.requireInput(options);

        //TODO
    }

}
