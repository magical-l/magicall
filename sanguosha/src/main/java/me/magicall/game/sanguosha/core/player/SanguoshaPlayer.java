package me.magicall.game.sanguosha.core.player;

import me.magicall.game.sanguosha.core.gaming.option.Options;
import me.magicall.game.sanguosha.core.gaming.option.Selection;

import java.util.Scanner;

/**
 * @author Liang Wenjian
 */
public class SanguoshaPlayer implements Player {

    private final Scanner scanner = new Scanner(System.in);

    public SanguoshaPlayer() {
        super();
    }

    @Override
    public <T extends Selection> T requireInput(final Options<T> options) {
        System.out.println(options.getMsg());
        while (!scanner.hasNext()) {
        }
        final String next = scanner.next();
        //TODO
        return null;
    }

    @Override
    public void output(final Object o) {
        //TODO
    }
}
