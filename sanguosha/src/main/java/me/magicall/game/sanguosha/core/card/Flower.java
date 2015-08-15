package me.magicall.game.sanguosha.core.card;

/**
 * 扑克牌的花色。
 *
 * @author Liang Wenjian
 */
public enum Flower {
    /**
     * 黑桃♠
     */
    SPADE(Color.BLACK),
    /**
     * 红桃♥
     */
    HEART(Color.RED),
    /**
     * 梅花♣
     */
    CLUB(Color.BLACK),
    /**
     * 方块♦
     */
    DIAMOND(Color.RED);

    public final Color color;

    Flower(final Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
