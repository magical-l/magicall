package me.magicall.game.card;

/**
 * @author Liang Wenjian
 */
public class Event {

    private final Object source;

    public Event(final Object source) {
        this.source = source;
    }

    public Object getSource() {
        return source;
    }

    public boolean isEnd() {
        return false;//TODO
    }
}
