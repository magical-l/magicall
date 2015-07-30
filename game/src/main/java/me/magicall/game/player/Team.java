package me.magicall.game.player;

import me.magicall.game.GameElement;
import me.magicall.mark.HasName;
import me.magicall.mark.HasOrder;

/**
 * 游戏中的团队。与具体的一场游戏无关。有的游戏每个团队只有一个游戏角色（PlayerRole），有些则更多。
 * 比如，在象棋中，Team红方、黑方；
 * 在dota中，Team是近卫军团、天灾军团；
 * 在8人局三国杀中，Team是主忠方、反贼、内奸；
 * 在三国杀国战中，Team是魏国、蜀国、吴国、群雄、野心家；
 * 
 * @author MaGiCalL
 */
public interface Team extends HasName, HasOrder, Comparable<Team>, GameElement {

	PlayerRole[] getPlayerRoles();
}
