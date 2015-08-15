package me.magicall.game.sanguosha.core.gaming;

/**
 * @author Liang Wenjian
 */
public class GameException extends RuntimeException {

    private static final long serialVersionUID = -6223412948377744491L;

    public GameException() {
        super();
    }

    public GameException(final Exception e) {
        super(e);
    }
}
