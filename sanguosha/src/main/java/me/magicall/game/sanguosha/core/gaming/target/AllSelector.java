package me.magicall.game.sanguosha.core.gaming.target;

import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.player.GamingPlayer;
import me.magicall.game.sanguosha.core.skill.Skill;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 直接选择全体。适用于【桃园结义】
 *
 * @author Liang Wenjian
 */
public class AllSelector implements Selector {

    public AllSelector() {
        super();
    }

    @Override
    public List<Hero> getTarget(final Sanguosha game, final GamingPlayer user, final Skill skill) {
        return game.getSurvivors().stream().map(GamingPlayer::getHero).collect(Collectors.toList());
    }
}
