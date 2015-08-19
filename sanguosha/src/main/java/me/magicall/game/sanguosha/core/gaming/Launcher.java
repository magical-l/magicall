package me.magicall.game.sanguosha.core.gaming;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.magicall.game.card.CardCfg;
import me.magicall.game.sanguosha.core.card.CardTypes;
import me.magicall.game.sanguosha.core.card.Flower;
import me.magicall.game.sanguosha.core.player.ConsoleIO;
import me.magicall.game.sanguosha.core.player.IO;
import me.magicall.game.sanguosha.core.player.Role;
import me.magicall.game.sanguosha.core.unit.Country;
import me.magicall.game.sanguosha.core.unit.Gender;
import me.magicall.game.sanguosha.core.unit.HeroCfg;

import java.util.Collection;
import java.util.Map;

/**
 * @author Liang Wenjian
 */
public class Launcher {

    public Launcher() {
        super();
    }

    public static void main(final String... args) {

        final int playerCount = 8;

        final Collection<IO> ios = Lists.newArrayList();
        for (int i = 0; i < playerCount; i++) {
            ios.add(new ConsoleIO());
        }

        final Collection<HeroCfg> heroCfgs = Lists.newArrayList(//
                new HeroCfg("曹操", 4, Country.魏, Gender.男),//
                new HeroCfg("刘备", 4, Country.蜀, Gender.男),//
                new HeroCfg("孙权", 4, Country.吴, Gender.男),//
                new HeroCfg("诸葛亮", 3, Country.蜀, Gender.男),//
                new HeroCfg("黄月英", 3, Country.蜀, Gender.女),//
                new HeroCfg("关羽", 4, Country.蜀, Gender.男),//
                new HeroCfg("张飞", 4, Country.蜀, Gender.男),//
                new HeroCfg("赵云", 4, Country.蜀, Gender.男),//
                new HeroCfg("马超", 4, Country.蜀, Gender.男),//
                new HeroCfg("司马懿", 3, Country.魏, Gender.男),//
                new HeroCfg("许褚", 4, Country.魏, Gender.男),//
                new HeroCfg("孙尚香", 3, Country.吴, Gender.女, Lists.newArrayList())//
        );
        //TODO

        //参考http://gw.sanguosha.com/data/newsDetail.asp?id=2&CategoryID=5007
        final Map<CardCfg, Integer> countOfCardCfg = Maps.newHashMap();
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 7, Flower.SPADE, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 8, Flower.SPADE, Lists.newArrayList()), 2);
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 9, Flower.SPADE, Lists.newArrayList()), 2);
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 10, Flower.SPADE, Lists.newArrayList()), 2);
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 10, Flower.HEART, Lists.newArrayList()), 2);
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 11, Flower.HEART, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 2, Flower.CLUB, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 3, Flower.CLUB, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 4, Flower.CLUB, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 5, Flower.CLUB, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 6, Flower.CLUB, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 7, Flower.CLUB, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 8, Flower.CLUB, Lists.newArrayList()), 2);
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 9, Flower.CLUB, Lists.newArrayList()), 2);
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 10, Flower.CLUB, Lists.newArrayList()), 2);
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 11, Flower.CLUB, Lists.newArrayList()), 2);
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 6, Flower.DIAMOND, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 7, Flower.DIAMOND, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 8, Flower.DIAMOND, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 9, Flower.DIAMOND, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 10, Flower.DIAMOND, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("杀", CardTypes.基本牌, 13, Flower.DIAMOND, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("闪", CardTypes.基本牌, 2, Flower.HEART, Lists.newArrayList()), 2);
        countOfCardCfg.put(new CardCfg("闪", CardTypes.基本牌, 13, Flower.HEART, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("闪", CardTypes.基本牌, 2, Flower.DIAMOND, Lists.newArrayList()), 2);
        countOfCardCfg.put(new CardCfg("闪", CardTypes.基本牌, 3, Flower.DIAMOND, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("闪", CardTypes.基本牌, 4, Flower.DIAMOND, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("闪", CardTypes.基本牌, 5, Flower.DIAMOND, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("闪", CardTypes.基本牌, 6, Flower.DIAMOND, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("闪", CardTypes.基本牌, 7, Flower.DIAMOND, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("闪", CardTypes.基本牌, 8, Flower.DIAMOND, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("闪", CardTypes.基本牌, 9, Flower.DIAMOND, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("闪", CardTypes.基本牌, 10, Flower.DIAMOND, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("闪", CardTypes.基本牌, 11, Flower.DIAMOND, Lists.newArrayList()), 2);
        countOfCardCfg.put(new CardCfg("桃", CardTypes.基本牌, 3, Flower.HEART, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("桃", CardTypes.基本牌, 4, Flower.HEART, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("桃", CardTypes.基本牌, 6, Flower.HEART, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("桃", CardTypes.基本牌, 7, Flower.HEART, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("桃", CardTypes.基本牌, 8, Flower.HEART, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("桃", CardTypes.基本牌, 9, Flower.HEART, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("桃", CardTypes.基本牌, 12, Flower.HEART, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("桃", CardTypes.基本牌, 12, Flower.DIAMOND, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("诸葛连弩", CardTypes.武器, 1, Flower.CLUB, Lists.newArrayList()), 1);
        countOfCardCfg.put(new CardCfg("诸葛连弩", CardTypes.武器, 1, Flower.DIAMOND, Lists.newArrayList()), 1);
        //TODO

        final Map<Role, Integer> countOfRole = Maps.newHashMap();
        countOfRole.put(Role.主公, 1);
        countOfRole.put(Role.忠臣, 2);
        countOfRole.put(Role.反贼, 4);
        countOfRole.put(Role.内奸, 1);

        final GamingCfg cfg = new SanguoshaCfg(ios, heroCfgs, countOfCardCfg, false, countOfRole);
        final Sanguosha game = new Sanguosha(cfg);
        game.play();
    }
}
