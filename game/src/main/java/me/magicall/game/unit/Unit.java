package me.magicall.game.unit;

import me.magicall.game.skill.SkillOwner;
import me.magicall.mark.Named;

/**
 * 单位,与具体一场游戏无关。在某场游戏中可能会有某种单位的若干实例GameUnit，它们是与该场游戏有关的。
 * 在魔兽争霸对战中，每个玩家可能有众多的单位，包括建筑、兵、英雄等；
 * 在dota中，“英雄”是一个单位，小兵也是一个单位；
 * 在三国杀中，“武将”是一个单位；
 * 在中国象棋中，帅士相车马炮兵都是单位。
 * 
 * @author MaGiCalL
 */
public interface Unit extends Named, SkillOwner {

}
