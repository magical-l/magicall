package me.magicall.game.sanguosha.core.gaming.stage;

import me.magicall.game.card.Card;
import me.magicall.game.sanguosha.core.gaming.Sanguosha;
import me.magicall.game.sanguosha.core.gaming.option.SkillSelection;
import me.magicall.game.sanguosha.core.skill.Skill;
import me.magicall.game.sanguosha.core.unit.Hero;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 出牌阶段。
 * 出牌阶段：进行游戏的主要阶段。
 * 出牌阶段的空闲时间点：能发动的技能：【挟缠】、【强袭】、【驱虎】、【急袭】、【排异】、【峻刑】、【颂词】、【仁德】、【忠义①】、【挑衅】、【眩惑（旧将）】、【心战】、【举荐（旧将）】、【制衡】、【苦肉】、【反间】、【结姻】、【制霸】、【天义】、【缔盟】、【直谏】、【奋迅】、【尚义】、【甘露】、【安恤】、【弓骑】、【解烦】、【黩武】、【青囊】、【普济】、【离间】、【乱武】、【黄天】、【祸水①】、【倾城】、【雄异】、【谋诛】、【明策】、【陷阵】、【焚城】、【密诏】、【咒缚①】、【攻心】、【业炎】、【无前】、【神愤】、【极略（制衡）】、【极略（完杀）】。能使用的牌：除【闪】以外的所有基本牌、除【无懈可击】和【无懈可击·国】以外的所有锦囊牌、所有装备牌。
 * ◆发动时机为“出牌阶段”的技能并非在出牌阶段的任意时候都能发动，而是必须在出牌阶段的空闲时间点才可以发动。如果在出牌阶段发生事件，就要先插入进行该事件的结算，虽然此时依然处于出牌阶段，但并非空闲时间点，须等到该事件结算完毕后才可以发动。
 * ◆出牌阶段有无数个空闲时间点，每一个空闲时间点都是一个独立的时机，角色在此时机可以发动相应的技能/使用相应的牌，但如果此技能/牌中说明“出牌阶段限一次”，则你在一个出牌阶段，只能在一个空闲时间点发动一次此技能/使用一次此牌。
 * 出牌阶段结束时：角色不想使用或无法使用牌时，便进入此时机。
 * 能发动的技能：【精策】、【生息】。
 *
 * @author Liang Wenjian
 */
public class PlayerPlayStage extends AbsStage {

    public PlayerPlayStage(final Sanguosha game, final Hero hero) {
        super(game, hero);
    }

    @Override
    protected void playInternal() {
        final Hero owner = getOwner();
        final Sanguosha game = getGame();
        while (true) {
            final SkillSelection skillSelection = owner.getPlayer().requireInput(new PlayerPlayOptions(owner));
            final Integer skillId = skillSelection.getSkillId();
            if (skillId == null) {
                //点击了“结束”
                return;
            }

            final List<Integer> resourceIds = skillSelection.getResourceIds();
            final List<Integer> targetIds = skillSelection.getTargetIds();
            final Collection<Card> resources = resourceIds.stream().map(game::getCard).collect(Collectors.toList());
            final Skill skill = game.getSkill(skillId);
            final List<Hero> targets = targetIds.stream().map(e -> game.getPlayer(e).getHero())
                                                .collect(Collectors.toList());
            game.skillAction(skill, owner, resources, targets);
        }
    }
}
