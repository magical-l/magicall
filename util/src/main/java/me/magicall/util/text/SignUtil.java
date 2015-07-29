package me.magicall.util.text;

public class SignUtil {

	/**
	 * 半角转全角
	 * 
	 * @param input String.
	 * @return 全角字符串.
	 */
	public static String toFullWidth(final String input) {
		final char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ' ') {
				c[i] = '\u3000';
			} else if (c[i] < '\177') {
				c[i] = (char) (c[i] + 65248);
			}
		}
		return new String(c);
	}

	/**
	 * 全角转半角
	 * 
	 * @param input String.
	 * @return 半角字符串
	 */
	public static String toHalfWidth(final String input) {

		final char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);
			}
		}
		final String returnString = new String(c);

		return returnString;
	}

	public static void main(final String... args) {
		final String full = toFullWidth(",");
		System.out.println("@@@@@@SignUtil.main():" + full + 'a' + toHalfWidth(full) + 'a');
	}
}
