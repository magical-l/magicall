package me.magicall.util;

import java.io.FileNotFoundException;
import java.util.Scanner;

import me.magicall.util.kit.StrKit;

public class Test {//这是神马飞机

//	private final Map<String, String> nameToFullName = new HashMap<String, String>();

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(final String... args) throws Exception {
		/*
		 * a();
		 * b();
		 * testFiltComment();
		 * testFiltString();
		 */
	}

	/**
	 * @return
	 * @throws FileNotFoundException
	 */
	/**
	 * @return
	 * @throws FileNotFoundException
	 */
	private static String readSource() throws FileNotFoundException {
		final String path = "I:\\workspace\\eclipsws\\all\\util\\src\\test\\java\\me\\magicall\\util\\Test.java";
		final java.io.File file = new java.io.File(path);
		final Scanner scanner = new Scanner(file);
		final StringBuilder sb = new StringBuilder();
		while (scanner.hasNextLine()) {
			sb.append(scanner.nextLine()).append(StrKit.newLine());
		}
		scanner.close();
		final String string = sb.toString();
		return string;
	}
}
