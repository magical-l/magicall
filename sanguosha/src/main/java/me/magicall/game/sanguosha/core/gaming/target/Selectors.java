package me.magicall.game.sanguosha.core.gaming.target;

import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.gaming.position.Position;
import me.magicall.game.sanguosha.core.gaming.position.PositionsOptions;
import me.magicall.game.sanguosha.core.gaming.position.PositionsSelection;
import me.magicall.game.sanguosha.core.player.GamingPlayer;
import me.magicall.game.sanguosha.core.skill.Skill;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Liang Wenjian
 */
public enum Selectors implements Selector {
    ONE {
        @Override
        public List<Hero> getTarget(final Sanguosha game, final GamingPlayer user, final Skill skill) {
            final List<Position> positions = game.getSurvivors().stream()//
                    .filter(e -> !Objects.equals(e, user))//
                    .map(GamingPlayer::getPosition)//
                    .collect(Collectors.toList());
            final PositionsSelection positionsSelection = user.requireInput(new PositionsOptions(positions));
            final GamingPlayer player = game.getPlayer(positionsSelection.getPositions().get(0));
            return Collections.singletonList(player.getHero());
        }
    },
    SELF {
        @Override
        public List<Hero> getTarget(final Sanguosha game, final GamingPlayer user, final Skill skill) {
            return Collections.singletonList(user.getHero());
        }
    },
    OTHERS {
        @Override
        public List<Hero> getTarget(final Sanguosha game, final GamingPlayer user, final Skill skill) {
            return game.getSurvivors().stream()//
                    .filter(e -> Objects.equals(e, user))//
                    .map(GamingPlayer::getHero)//
                    .collect(Collectors.toList());
        }
    },
    ALL {
        @Override
        public List<Hero> getTarget(final Sanguosha game, final GamingPlayer user, final Skill skill) {
            return game.getSurvivors().stream().map(GamingPlayer::getHero).collect(Collectors.toList());
        }
    }
}
