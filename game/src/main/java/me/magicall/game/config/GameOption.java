package me.magicall.game.config;

import java.util.List;

import me.magicall.game.player.Player;

/**
 * 游戏的选项。
 * 
 * @author MaGiCalL
 */
public interface GameOption extends GameConfig {

	void setMainPlayer(Player player);

	/**
	 * 获取游戏的所有选项。
	 * 
	 * @return
	 */
	List<OptionItem> getOptionItems();

	/**
	 * 增加一个选项。
	 * 
	 * @param optionItem
	 */
	void addOptionItem(OptionItem optionItem);

}
