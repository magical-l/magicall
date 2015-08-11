package me.magicall.game.sanguosha.core.gaming;

import com.google.common.collect.Lists;
import me.magicall.game.card.Round;
import me.magicall.game.sanguosha.core.gaming.option.PlayerPlayOptions;
import me.magicall.game.sanguosha.core.gaming.option.SkillSelection;
import me.magicall.game.sanguosha.core.player.GamingPlayer;

import java.util.List;

/**
 * @author Liang Wenjian
 */
public class SanguoshaRound implements Round {

    private final Sanguosha game;

    private final List<UnitTurn> unitTurns;

    public SanguoshaRound(final Sanguosha game) {
        super();
        this.game = game;
        unitTurns = Lists.newArrayListWithExpectedSize(game.getPlayers().size());
    }

    @Override
    public void play() {
        for (final GamingPlayer gamingPlayer : game.getPlayers()) {
            gamingPlayer.getHeros().stream().forEach(hero -> {
                final SkillSelection skillSelection=gamingPlayer.getPlayer().requireInput(new PlayerPlayOptions(hero));
                final int skillId = skillSelection.getSkillId();
                final List<Integer> resourceIds = skillSelection.getResourceIds();
                final List<Integer> targetIds = skillSelection.getTargetIds();
                final UnitTurn unitTurn = null;
                unitTurns.add(unitTurn);
            });
        }
        //TODO
    }

    @Override
    public Sanguosha getGame() {
        return game;
    }
}
