package me.magicall.game.sanguosha.core.gaming.target;

import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.gaming.position.DistanceType;
import me.magicall.game.sanguosha.core.gaming.position.Position;
import me.magicall.game.sanguosha.core.gaming.position.PositionsOptions;
import me.magicall.game.sanguosha.core.player.GamingPlayer;
import me.magicall.game.sanguosha.core.skill.Skill;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 范围内选择器。
 *
 * @author Liang Wenjian
 */
public class InsideScopeSelector implements Selector {

    public static final boolean DEFAULT_NOT_INCLUDING_SELF = false;

    private final int maxCount;
    private final boolean includingSelf;
    private final DistanceType distanceType;

    public InsideScopeSelector(final DistanceType distanceType) {
        this(DEFAULT_NOT_INCLUDING_SELF, distanceType);
    }

    public InsideScopeSelector(final boolean includingSelf, final DistanceType distanceType) {
        this(1, includingSelf, distanceType);
    }

    public InsideScopeSelector(final int maxCount, final DistanceType distanceType) {
        this(maxCount, DEFAULT_NOT_INCLUDING_SELF, distanceType);
    }

    public InsideScopeSelector(final int maxCount, final boolean includingSelf, final DistanceType distanceType) {
        super();
        this.maxCount = maxCount;
        this.includingSelf = includingSelf;
        this.distanceType = distanceType;
    }

    @Override
    public List<Hero> getTarget(final Sanguosha game, final GamingPlayer user, final Skill skill) {
        final Map<GamingPlayer, Integer> playerDistances = distanceType.getPlayers(game, user, skill);
        final List<Position> positions = playerDistances.keySet().stream()//
                .filter(e -> includingSelf || !Objects.equals(e, user))//
                .map(GamingPlayer::getPosition)//
                .collect(Collectors.toList());
        return user.requireInput(//
                new PositionsOptions(maxCount, positions)).getPositions().stream()//
                .map(e -> game.getPlayer(e).getHero())//
                .collect(Collectors.toList());
    }
}
