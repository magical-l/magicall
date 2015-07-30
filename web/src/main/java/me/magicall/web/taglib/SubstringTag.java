package me.magicall.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import me.magicall.util.kit.Kits;

public class SubstringTag extends SimpleTagSupport {

	private String value;
	private int fromIndex;
	private int len;
	private String end = "...";

	private static class Item {

		char start; // 包含
		char end; // 包含
		int score = 10;// 1 - 10
		int[] scores;

		Item(final char start, final char end, final int score) {
			this.start = start;
			this.end = end;
			this.score = score;
		}

		Item(final char start, final char end, final int... scores) {
			this.start = start;
			this.end = end;
			this.scores = scores;
		}

		int getScore(final char ch) {
			if (start <= ch && ch <= end) {
				if (scores != null) {
					return scores[ch - start];
				}
				return score;
			}
			return 0;
		}
	}

	private static final int MAX_SCORE = 20;
	private static final Item[] WORD_ITEMS = { // 越靠前，权重越大
			new Item('\u4e00', '\u9fa5', MAX_SCORE), // 中文
			new Item('\uff00', '\uffef', MAX_SCORE), // 全角ASCII、全角中英文标点、半宽片假名、半宽平假名、半宽韩文字母
			new Item('\uac00', '\ud7af', MAX_SCORE), // 韩文拼音：AC00-D7AF
			new Item('\u1100', '\u11ff', MAX_SCORE), // 韩文字母：1100-11FF
			new Item('\u3130', '\u318f', MAX_SCORE), // 韩文兼容字母：3130-318F
			new Item('\u3040', '\u309f', MAX_SCORE), //  日文平假名：3040-309F
			new Item('\u30a0', '\u30ff', MAX_SCORE), // 日文片假名：30A0-30FF
			new Item('\u31f0', '\u31ff', MAX_SCORE), // 日文片假名拼音扩展：31F0-31FF
			new Item('\u0020', '\u007e', new int[] { 7, 7, 9, 15, 11, 20, 14, 5, 8, 8, 11, 15, 7, 8, 7, 8, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 8, 8, 15,
					15, 15, 10, 19, 12, 12, 13, 14, 12, 11, 14, 14, 8, 9, 12, 10, 16, 14, 15, 12, 15, 13, 12, 12, 14, 12, 19, 12, 12, 12, 8, 8, 8, 15, 11, 11,
					11, 12, 10, 12, 11, 7, 12, 12, 5, 6, 10, 5, 17, 12, 11, 12, 12, 8, 9, 7, 12, 10, 15, 10, 10, 9, 10, 8, 10, 15 }),//英文
			new Item('\u0000', '\u024f', 13) // 英文
	};

	private static int getScore(final char ch) {
		for (final Item item : WORD_ITEMS) {
			final int s = item.getScore(ch);
			if (s > 0) {
				return s;
			}
		}
		return MAX_SCORE; // 宁可大一点儿
	}

	private String subString(final String original, final String end, final int offset, final int maxLen) {
		if (original == null) {
			return null;
		}

		final int maxScore = maxLen * MAX_SCORE;
		int sumScore = 0;
		int sumScoreWithEnd = 0;
		if (end != null) {
			for (final char ch : end.toCharArray()) {
				sumScoreWithEnd += getScore(ch);
			}
		}

		int len = -1;
		int lenWithEnd = -1;

		final char[] charArr = original.toCharArray();
		int index;
		for (index = offset; index < charArr.length; ++index) {
			final char ch = charArr[index];
			final int score = getScore(ch);

			sumScoreWithEnd += score;
			if (sumScoreWithEnd <= maxScore) {
				lenWithEnd = index - offset + 1;
			}

			sumScore += score;
			if (sumScore > maxScore) {
				len = index - offset;
				break;// 超过了
			}
		}
		String result = original;
		result = result.replace('\\', ' ').replace('\'', ' ').replace('"', ' ');
		if (index == charArr.length) { // 遍历完所有的，还是没有超过
			result = new String(result.substring(offset));
		} else if (lenWithEnd > 0) {
			result = result.substring(offset, offset + lenWithEnd) + end;
		} else if (len > 0) {
			result = result.substring(offset, offset + len);
		} else {
			// nothing
		}
		return result;
	}

	@Override
	public void doTag() throws JspException, IOException {
		final String result;
		if (Kits.STR.isEmpty(value)) {
			result = Kits.STR.emptyValue();
		} else {
			result = subString(value, end, Math.max(0, fromIndex), len);
		}
		getJspContext().getOut().write(result);
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public int getFromIndex() {
		return fromIndex;
	}

	public void setFromIndex(final int fromIndex) {
		this.fromIndex = fromIndex;
	}

	public int getLen() {
		return len;
	}

	public void setLen(final int len) {
		this.len = len;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(final String end) {
		this.end = end;
	}

	public static void main(final String... args) {
		final int[] a = new int[] { 7, 7, 9, 15, 11, 20, 14, 5, 8, 8, 11, 15, 7, 8, 7, 8, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 8, 8, 15, 15, 15, 10, 19, 12,
				12, 13, 14, 12, 11, 14, 14, 8, 9, 12, 10, 16, 14, 15, 12, 15, 13, 12, 12, 14, 12, 19, 12, 12, 12, 8, 8, 8, 15, 11, 11, 11, 12, 10, 12, 11, 7,
				12, 12, 5, 6, 10, 5, 17, 12, 11, 12, 12, 8, 9, 7, 12, 10, 15, 10, 10, 9, 10, 8, 10, 15 };
		System.out.println(a.length);
	}
}
