package me.magicall.game.sanguosha.core.card.cfg;

import me.magicall.game.card.CardCfg;
import me.magicall.game.sanguosha.core.card.CardTypes;
import me.magicall.game.sanguosha.core.card.Flower;
import me.magicall.game.sanguosha.core.skill.Skill;

/**
 * @author Liang Wenjian
 */
public class 仁王盾 extends CardCfg {

    public 仁王盾(final int point, final Flower flower) {
        super(CardTypes.防具, point, flower, (Skill) null);//TODO
    }
}
