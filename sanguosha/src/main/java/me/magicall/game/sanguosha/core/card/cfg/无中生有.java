package me.magicall.game.sanguosha.core.card.cfg;

import me.magicall.game.card.CardCfg;
import me.magicall.game.sanguosha.core.card.CardTypes;
import me.magicall.game.sanguosha.core.card.Flower;
import me.magicall.game.sanguosha.core.skill.Skill;

/**
 * @author Liang Wenjian
 */
public class 无中生有 extends CardCfg {

    public 无中生有(final int point, final Flower flower) {
        super(CardTypes.非延时锦囊, point, flower, (Skill) null);//TODO
    }
}
