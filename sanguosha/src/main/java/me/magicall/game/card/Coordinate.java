package me.magicall.game.card;

/**
 * @author Liang Wenjian
 */
@FunctionalInterface
public interface Coordinate<THIS_TYPE extends Coordinate<THIS_TYPE, DISTANCE_TYPE>, DISTANCE_TYPE> {

    DISTANCE_TYPE distance(final THIS_TYPE other);
}
