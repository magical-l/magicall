package me.magicall.game.config;

import me.magicall.game.io.GameOutput;
import me.magicall.game.io.OutputSource;
import me.magicall.mark.Named;

/**
 * 游戏参数选项。
 * 
 * @author MaGiCalL
 * @param <T>
 */
public interface OptionItem extends Named, OutputSource {

	/**
	 * 输出本参数可能的值或者提示
	 * 
	 * @param gameOutput
	 */
	void showAvailableValues(GameOutput gameOutput);

	/**
	 * 设置本参数的值
	 * 
	 * @param gameOption
	 * @param value
	 */
	void setValue(GameOption gameOption, Object inputObject) throws IllegalOptionValueException;

}
