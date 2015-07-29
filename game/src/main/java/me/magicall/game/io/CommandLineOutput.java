package me.magicall.game.io;

/**
 * 命令行输出。
 * 
 * @author MaGiCalL
 */
public class CommandLineOutput implements GameOutput {

	@Override
	public void output(final OutputSource outputSource, final Object content) {
//		System.out.println(outputSource.getName() + " : ");
		System.out.println(content);
	}
}
