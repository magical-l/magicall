package me.magicall.game.sanguosha.core.gaming.position;

import me.magicall.game.sanguosha.core.gaming.GameException;
import me.magicall.game.sanguosha.core.gaming.option.Options;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 目标选项。
 *
 * @author Liang Wenjian
 */
public class PositionsOptions implements Options<PositionsSelection> {

    private final int maxCount;
    private final List<Position> positions;

    public PositionsOptions(final List<Position> positions) {
        this(1, positions);
    }

    public PositionsOptions(final int maxCount, final List<Position> positions) {
        super();
        this.maxCount = maxCount;
        this.positions = positions;
    }

    @Override
    public String getMsg() {
        return "请选择最多" + maxCount + "个目标。";
    }

    @Override
    public List<?> getOptions() {
        return positions;
    }

    @Override
    public PositionsSelection toSelection(final String input) {
        final String[] split = input.split(",");
        if (split.length > maxCount) {
            throw new GameException();
        }
        return new PositionsSelection(Arrays.asList(split).stream()//
                .map(Integer::parseInt)//
                .collect(Collectors.toList()));
    }

}
