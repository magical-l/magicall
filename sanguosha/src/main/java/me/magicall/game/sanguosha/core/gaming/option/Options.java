package me.magicall.game.sanguosha.core.gaming.option;

import java.util.List;

/**
 * @author Liang Wenjian
 */
public interface Options<T extends Selection> {
    /**
     * 提示给玩家的信息。
     *
     * @return
     */
    String getMsg();

    /**
     * 选项
     *
     * @return
     */
    List<?> getOptions();

    /**
     * 玩家输入的信息，转化为操作。
     *
     * @param input
     * @return
     */
    T toSelection(final String input);
}
