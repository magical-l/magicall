package me.magicall.game.player;

import me.magicall.game.GameElement;
import me.magicall.game.io.OutputSource;
import me.magicall.game.skill.SkillOwner;
import me.magicall.mark.HasNameGetter;
import me.magicall.mark.HasOrder;

/**
 * 玩家角色。一个玩家角色对象属于一场游戏，是玩家和游戏之间的接口，玩家角色拥有输入（对程序而言），玩家使用玩家角色的输入口来输入指令玩游戏。
 * 在一场游戏里，每个角色都有所属的玩家，每个玩家都有一个玩家角色。
 * 这个对象与Player对象很像，注意区别，有时候还可以是同一个对象。
 * 在三国杀8人局中，“主忠方”是Team的一个实例，“主公”、“忠臣”、“反贼”、“内奸”是PlayerRole的四个实例，但每种角色有1~4名“玩家”实例。
 * 在dota中，“近卫军团”是Team的一个实例，“玩家1（红色玩家）”、“玩家12（电脑）”都是PlayerRole的实例，也都是Player的实例。
 * 在中国象棋中，“红方”、“黑方”既是Team，也是玩家角色，玩家可以是“人”、“低级电脑”、“中级电脑”、“高级电脑”等。
 * 在数独中，只有一个玩家角色，玩家可以是AI1、AI2、AI3、人等。
 * 另外的游戏中，玩家角色可能是“前锋”、“中锋”、“后卫”，但可能若干个玩家都是同一种角色。
 * 
 * @author MaGiCalL
 */
public interface PlayerRole extends HasNameGetter, SkillOwner, HasOrder, OutputSource, GameElement {

	Player getPlayer();

	void setPlayer(Player player);
}
