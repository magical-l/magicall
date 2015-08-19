package me.magicall.game.sanguosha.core.gaming.position;

import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.player.GamingPlayer;
import me.magicall.game.sanguosha.core.skill.Skill;

import java.util.Map;

/**
 * @author Liang Wenjian
 */
public enum DistanceType {
    ATTACK_DISTANCE {
        @Override
        public Map<GamingPlayer, Integer> getPlayers(final Sanguosha game, final GamingPlayer user, final Skill skill) {
            return game.calculateAttackables(user);
        }
    },
    POSITION_DISTANCE {
        @Override
        public Map<GamingPlayer, Integer> getPlayers(final Sanguosha game, final GamingPlayer user, final Skill skill) {
            return game.getDistanceScope(user);
        }
    };

    public abstract Map<GamingPlayer, Integer> getPlayers(Sanguosha game, GamingPlayer user, Skill skill);
}
