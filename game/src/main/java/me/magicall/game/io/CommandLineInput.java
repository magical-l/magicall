package me.magicall.game.io;

import java.util.Scanner;

/**
 * 命令行输入。
 * 
 * @author MaGiCalL
 */
public class CommandLineInput extends AbsGameInput {

	private final Scanner scanner = new Scanner(System.in);

	@Override
	protected Object nextInput() {
		return scanner.nextLine().trim();
	}

}
