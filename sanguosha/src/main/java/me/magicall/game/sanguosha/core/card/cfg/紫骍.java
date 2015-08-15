package me.magicall.game.sanguosha.core.card.cfg;

import me.magicall.game.card.CardCfg;
import me.magicall.game.sanguosha.core.card.CardTypes;
import me.magicall.game.sanguosha.core.card.Flower;
import me.magicall.game.sanguosha.core.skill.Skill;

/**
 * @author Liang Wenjian
 */
public class 紫骍 extends CardCfg {

    public 紫骍(final int point, final Flower flower) {
        super(CardTypes.ATTACK_HORSE, point, flower, (Skill) null);//TODO
    }
}
