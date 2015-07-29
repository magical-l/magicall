package me.magicall.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.magicall.util.kit.StrKit;

public class ClassesUsedAnalyzer {

	private static final String JAVA_NAME_PART = "\\s*[a-z$_](?:\\w|$)*\\s*";

	private static final Pattern JAVA_NAME_PATTERN = Pattern.compile(//
			"[\\W^.]+(((?:" + JAVA_NAME_PART + "\\.)+(" + JAVA_NAME_PART + "))\\s*\\W)"//
			, Pattern.CASE_INSENSITIVE);

	private static final Pattern COMMENTS_PATTERN = Pattern.compile("/\\*.*?\\*/", Pattern.DOTALL);
	private static final Pattern SINGLE_COLUMN_COMMENTS_PATTERN = Pattern.compile("//.*");

	private static ClassesUsedAnalyzer INSTANCE = new ClassesUsedAnalyzer();

	private static String filtComments(final String sourceCode) {
		return COMMENTS_PATTERN.matcher(sourceCode).replaceAll("");
	}

	private static String filtLineComments(final String sourceCode) {
		return SINGLE_COLUMN_COMMENTS_PATTERN.matcher(sourceCode).replaceAll("");
	}

	private static String filtString(final String sourceCode) {
		final StringBuilder sb = new StringBuilder();
		final int len = sourceCode.length();

		boolean quot = false;
		boolean slashed = false;
		boolean singleQuot = false;

		for (int i = 0; i < len; ++i) {
			final char c = sourceCode.charAt(i);
			if (c == '"') {
				if (quot || singleQuot) {
					if (slashed) {
						slashed = false;
					} else {
						quot = false;
					}
				} else {
					quot = true;
				}
			} else if (c == '\\') {
				slashed = !slashed;
			} else if (c == '\'') {
				if (slashed) {
					slashed = false;
				} else {
					singleQuot = !singleQuot;
				}
			} else {
				if (slashed) {
					assert quot || singleQuot;
					slashed = false;
				} else {
					if (!quot) {
						sb.append(c);
					}
				}
			}
		}
		return sb.toString();
	}

	private final Set<String> usedClassesNames = new HashSet<>();
	private final Set<String> shortNames = new HashSet<>();

	/**
	 * @param sourceCode
	 */
	public void analyze(final String sourceCode) {
		String s = filtComments(sourceCode);
		s = filtLineComments(s);
		s = filtString(s);
		s = handlePackage(s);
		s = handleImport(s);

		final Matcher matcher = JAVA_NAME_PATTERN.matcher(s);
		final int groupCount = matcher.groupCount();
		System.out.println("@@@@@@ImportsAnalyzer.analyze():" + groupCount);

		for (int start = 0; matcher.find(start); start = matcher.end() - 1) {
//			for (int i = 1; i <= groupCount; ++i) {
//				System.out.println("@@@@@@ImportsAnalyzer.analyze():" + matcher.group(i));
//			}
			//group1的可能性是
			//1,在声明一个对象：packageName.packageName...ClassName （这里有个空白）
			//2.1,在对对象使用一个方法：((packageName...)ClassName.(STATIC_FIELD...))varName.method(
			//2.2,在使用一个静态方法：(packageName...)ClassName.method(
			//3.1,在对对象变量做运算：(((packageName...)ClassName.(STATIC_FIELD...))|varName.)varName+或-或*或/……
			//3.2,在对静态变量做运算：(packageName...)ClassName.(STATIC_FIELD...)STATIC_FIELD+或-或*或/……
			//倒数第二段要么是一个变量名，要么是类名，再往前则还可能是包名
			final char nextToName = StrKit.lastChar(matcher.group(1));
			if (Character.isWhitespace(nextToName)) {//a class
				addUsedClassName(matcher.group(2));
			} else {//a method or a field
				final String group2 = matcher.group(2);
				final String callerName = getClassName(group2, 0);

//				if (!shortNames.contains(callerName)) {
//					addUsedClassName(callerName);
//				}
			}
		}
	}

	private String handlePackage(final String sourceCode) {
		final String keyword = "package";
		final int indexOfPackage = sourceCode.indexOf(keyword);
		if (indexOfPackage < 0) {
			return sourceCode;
		}
		final int indexOfSemicolon = sourceCode.indexOf(';', indexOfPackage + keyword.length());
		return sourceCode.substring(indexOfSemicolon + 1);
	}

	private String handleImport(final String sourceCode) {
		final String keyword = "import";
		String s = sourceCode;

		for (int i = s.indexOf(keyword); i >= 0; i = s.indexOf(keyword)) {
			final int fromIndex = i + keyword.length() + 1;
			final int indexOfSemicolon = s.indexOf(';', fromIndex);

			final int indexOfStatic = s.indexOf("static", fromIndex);
			final int indexNextToStatic = indexOfStatic + "static".length() + 1;

			final String fullName;
			if (indexOfStatic >= 0//
					&& indexOfStatic < indexOfSemicolon//
					&& Character.isWhitespace(s.charAt(indexOfStatic - 1))//
					&& Character.isWhitespace(s.charAt(indexNextToStatic))) {//import static
				fullName = getClassName(s, indexNextToStatic);
				//FIXME:忽略了import static packageName.className.fieldName.fieldName这种多个fieldName传递的情况。
			} else {
				fullName = s.substring(fromIndex, indexOfSemicolon).trim();
			}
			addUsedClassName(fullName);
			s = s.substring(indexOfSemicolon + 1);
		}
		System.out.println("@@@@@@ClassesUsedAnalyzer.handleImport():" + usedClassesNames);
		return s;
	}

	private String getClassName(final String nameWithFieldOrMethodName, final int fromIndex) {
		return nameWithFieldOrMethodName.substring(fromIndex, nameWithFieldOrMethodName.lastIndexOf('.')).trim();
	}

	private void addUsedClassName(final String fullName) {
		usedClassesNames.add(fullName);
		shortNames.add(fullName.substring(fullName.lastIndexOf('.')));
	}

	public static void main(final String... args) throws Exception {
		testAnalyze();
//		testFiltString();
//		testFiltComment();
//		testFiltLineComments();
//		testHandleImport();
		System.out.println("@@@@@@ClassesUsedAnalyzer.main():end");
	}

	static void testHandleImport() throws Exception {
		final String sourceCode = readSource();
		final String s = INSTANCE.handleImport(sourceCode);
//		System.out.println(s);
	}

	static void testFiltLineComments() throws Exception {
		final String sourceCode = readSource();
		final String s = filtLineComments(sourceCode);
		System.out.println(s);
	}

	static void testFiltComment() throws Exception {
		final String string = "shenme /** ji\rji\n\r\r\nway\nway*/laji";
		assert string != null;
		final String sourceCode = readSource();
		final String s = filtComments(sourceCode);
		System.out.println(s);
	}

	static void testFiltString() throws Exception {
		final String string = "\\\\\"\\\"icu\"niao";
		assert string != null;

		final char c = '\\';
		assert c != 0;

		String sourceCode = readSource();
		sourceCode = sourceCode.substring(sourceCode.indexOf("private String filtString(final String sourceCode) {")
				+ "private String filtString(final String sourceCode) {".length());
		System.out.println(filtString(sourceCode));
	}

	static void testAnalyze() throws Exception {
		final String string = readSource();
		INSTANCE.analyze(string);
	}

	/**
	 * @return
	 * @throws FileNotFoundException
	 */
	private static String readSource() throws FileNotFoundException {
		final String path = "I:\\workspace\\eclipsws\\all\\util\\src\\test\\java\\me\\magicall\\util\\Test.java";
		final File file = new File(path);
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
