package me.magicall.game.sanguosha.core.gaming.target;

import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.player.GamingPlayer;
import me.magicall.game.sanguosha.core.skill.Skill;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * #其他角色
 * 区分于“你（即牌或技能的使用者）”。
 *
 * @author Liang Wenjian
 */
public class OthersSelector implements Selector {

    public OthersSelector() {
        super();
    }

    @Override
    public List<Hero> getTarget(final Sanguosha game, final GamingPlayer user, final Skill skill) {
        return game.getSurvivors().stream()//
                .filter(e -> Objects.equals(e, user))//
                .map(GamingPlayer::getHero)//
                .collect(Collectors.toList());
    }
}
