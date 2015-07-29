package me.magicall.game.unit;

import me.magicall.game.GameElement;
import me.magicall.game.player.PlayerRole;

/**
 * 本类对象是某种单位在一场具体的游戏中的一个实例。
 * 
 * @author MaGiCalL
 */
public interface GamingUnit extends Unit, GameElement {

	PlayerRole getOwner();

	void setOwner(PlayerRole owner);
}
