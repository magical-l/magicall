package me.magicall.util.kit;

import me.magicall.coll.ElementTransformer;
import me.magicall.coll.ElementTransformerUtil;
import me.magicall.consts.CommonConst;
import me.magicall.consts.StrConst;
import me.magicall.consts.StrConst.JsonConst;

import java.util.Iterator;

import static me.magicall.consts.CommonConst.NOT_FOUND_INDEX;

abstract class CharSequenceKit<S extends CharSequence> extends Kit<S> {

	private static final long serialVersionUID = 6603815643326723361L;

	CharSequenceKit(final Class<S> mainClass, final S emptyValue, final String... shortNames) {
		super(mainClass, emptyValue, shortNames);
	}

	CharSequenceKit(final Class<S> mainClass, final S emptyValue) {
		super(mainClass, emptyValue);
	}
}

public class StrKit extends CharSequenceKit<String> {

	private static final String NEW_LINE = System.getProperty("line.separator");

	private static final Class<String> CLASS_ARR = String.class;

	private static final String[] SHORT_NAMES = { "str", "varchar" };

	//---------------------------------------
	public static final StrKit INSTANCE = new StrKit();

	public static final String EMPTY_JSON_OBJ = JsonConst.EMPTY_OBJ;

	public static final String EMPTY_JSON_ARR = JsonConst.EMPTY_ARR;

	//---------------------------------------
	private StrKit() {
		super(CLASS_ARR, StrConst.EMPTY_STR, SHORT_NAMES);
	}

	//---------------------------------------
	public static String newLine() {
		return INSTANCE.checkToDefaultValue(NEW_LINE, "\r\n");
	}

	public static String toUnicode(final String s) {
		final StringBuilder uStr = new StringBuilder(Kits.STR.emptyValue());
		if (s == null) {
			return "";
		}
		final int size = s.length();
		for (int i = 0; i < size; i++) {
			final int iValue = s.codePointAt(i);
			if (iValue < 16) {
				uStr.append("\\u000").append(Integer.toHexString(iValue));
			} else if (iValue < 256) {
				uStr.append("\\u00").append(Integer.toHexString(iValue));
			} else if (iValue < 4096) {
				uStr.append("\\u0").append(Integer.toHexString(iValue));
			} else {
				uStr.append("\\u").append(Integer.toHexString(iValue));
			}
		}
		return uStr.toString();
	}

	public static char lastChar(final String s) {
		return s.charAt(s.length() - 1);
	}

	@Override
	public String fromString(final String source) {
		return checkToEmptyValue(source);
	}

	@Override
	public boolean isEmpty(final String source) {
		return source == null || source.trim().isEmpty();
	}

	public boolean startsWithIgnoreCase(final String source, final String target, final boolean escapeStartingBlank) {
		final int len = source.length();
		final int targetLen = target.length();
		int sourceIndex = 0;
		int targetIndex = 0;
		if (escapeStartingBlank) {
			for (; sourceIndex < len; ++sourceIndex) {
				final char c = source.charAt(sourceIndex);
				if (!Character.isWhitespace(c)) {
					break;
				}
			}
			for (; targetIndex < targetLen; ++targetIndex) {
				final char c = target.charAt(targetIndex);
				if (!Character.isWhitespace(c)) {
					break;
				}
			}
		}
		if (len - sourceIndex < targetLen - targetIndex) {
			return false;
		}
		while (sourceIndex < len && targetIndex < targetLen) {
			final char c = source.charAt(sourceIndex);
			final char c2 = target.charAt(targetIndex);

			if (!Kits.CHAR.equalsIgnoreCase(c, c2)) {
				return false;
			}

			++sourceIndex;
			++targetIndex;
		}
		if (targetIndex < targetLen) {//source比target短
			return false;
		}
		return true;
	}

	public boolean endsWithIgnoreCase(final String source, final String target, final boolean escapeEndingBlank) {
		int len = source.length();
		int targetLen = target.length();
		int sourceIndex = len - 1;
		int targetIndex = targetLen - 1;
		if (escapeEndingBlank) {
			for (; sourceIndex >= 0; --sourceIndex) {
				final char c = source.charAt(sourceIndex);
				if (!Character.isWhitespace(c)) {
					break;
				}
			}
			for (; targetIndex >= 0; --targetIndex) {
				final char c = target.charAt(targetIndex);
				if (!Character.isWhitespace(c)) {
					break;
				}
			}
		}
		if (len - sourceIndex < targetLen - targetIndex) {
			return false;
		}
		while (sourceIndex < --len && targetIndex < --targetLen) {
			final char c = source.charAt(len);
			final char c2 = target.charAt(targetLen);

			if (!Kits.CHAR.equalsIgnoreCase(c, c2)) {
				return false;
			}
		}
		if (targetIndex < targetLen) {//source比target短
			return false;
		}
		return true;
	}

	public <E> String join(final Iterable<E> iterable, final String seperator) {
		return join(iterable, seperator, ElementTransformerUtil.TO_STRING);
	}

	public <E> String join(final Iterable<E> iterable, final String seperator, final ElementTransformer<? super E, String> tf) {
		final StringBuilder sb = new StringBuilder();
		join(sb, iterable, seperator, tf);
		return sb.toString();
	}

	public <E> StringBuilder join(final StringBuilder sb, final Iterable<E> iterable, final String seperator) {
		return join(sb, iterable, seperator, ElementTransformerUtil.TO_STRING);
	}

	public <E> StringBuilder join(final StringBuilder sb, final Iterable<E> iterable, final String seperator, ElementTransformer<? super E, String> tf) {
		if (tf == null) {
			tf = ElementTransformerUtil.TO_STRING;
		}
		final Iterator<E> iterator = iterable.iterator();
		if (iterator.hasNext()) {
			int index = 0;
			sb.append(tf.transform(index, iterator.next()));
			++index;
			while (iterator.hasNext()) {
				sb.append(seperator).append(tf.transform(index++, iterator.next()));
			}
		}
		return sb;
	}

	public String middle(final String source, final String before, final boolean includeBefore, final String after, final boolean includeAfter) {
		final int beforeIndex = source.indexOf(before);
		final int startIndex;
		if (beforeIndex == NOT_FOUND_INDEX) {
			startIndex = 0;
		} else if (!includeBefore) {//注意里需要个叹号!
			startIndex = beforeIndex + before.length();
		} else {
			startIndex = beforeIndex;
		}
		final int afterIndex = source.indexOf(after, beforeIndex + before.length());
		final int endIndex;
		if (afterIndex == NOT_FOUND_INDEX) {
			endIndex = source.length();
		} else if (includeAfter) {
			endIndex = afterIndex + after.length();
		} else {
			endIndex = afterIndex;
		}
		return source.substring(startIndex, endIndex);
	}

	public String subStringAfter(final String source, final String seq, final int endIndex) {
		return source.substring(indexAfter(source, seq), endIndex);
	}

	public String subStringAfter(final String source, final String seq, final int fromIndex, final int endIndex) {
		return source.substring(indexAfter(source, seq, fromIndex), endIndex);
	}

	public String subStringBefore(final String source, final String seq, final boolean lastSeq) {
		final int index = lastSeq ? source.lastIndexOf(seq) : source.indexOf(seq);
		if (index == NOT_FOUND_INDEX) {
			return source;
		}
		return source.substring(0, index);
	}

	public String subStringAfter(final String source, final String seq) {
		return source.substring(indexAfter(source, seq));
	}

	public String subStringAfterLastSeq(final String source, final String seq) {
		final int lastIndexOf = source.lastIndexOf(seq);
		return lastIndexOf == NOT_FOUND_INDEX ? source : source.substring(lastIndexOf + seq.length());
	}

	public int indexAfter(final String source, final String seq) {
		return indexAfter(source, seq, 0);
	}

	public int indexAfter(final String source, final String seq, final int fromIndex) {
		final int index = source.indexOf(seq, fromIndex);
		return index == NOT_FOUND_INDEX ? fromIndex : index + seq.length();
	}

	public boolean containsIgnoreCase(final String longOne, final String shortOne) {
		return indexOfIgnoreCase(longOne, shortOne) != NOT_FOUND_INDEX;
	}

	public int indexOfIgnoreCase(final String longOne, final String shortOne) {
		final int longLen = longOne.length();
		final int targetLen = shortOne.length();
		if (longLen < targetLen) {
			return NOT_FOUND_INDEX;
		}
		final int max = longLen - targetLen;
		final char first = shortOne.charAt(0);

		for (int i = 0; i <= max; ++i) {
			/* Look for first character. */
			for (; i <= max && !Kits.CHAR.equalsIgnoreCase(longOne.charAt(i), first); ++i) {
			}

			/* Found first character, now look at the rest of v2 */
			if (i <= max) {
				int j = i + 1;
				final int end = j + targetLen - 1;
				for (int k = 1; j < end && Kits.CHAR.equalsIgnoreCase(longOne.charAt(j), shortOne.charAt(k)); j++, k++) {
					;
				}

				if (j == end) {
					/* Found whole string. */
					return i;
				}
			} else {
				return NOT_FOUND_INDEX;
			}
		}
		return NOT_FOUND_INDEX;
	}

	@Override
	public boolean equals(final String o1, final String o2) {
		return o1 == null ? o2 == null : o1.equalsIgnoreCase(o2);
	}

	public int findCharIgnoreCase(final char c, final String s, final int fromIndex, final int toIndex) {
		//由于我们比较时需要忽略大小写,所以不能用String的indexOf(int ch,int fromIndex)方法,只好自己写一个
		for (int i = fromIndex; i < toIndex; ++i) {
			if (Kits.CHAR.equalsIgnoreCase(c, s.charAt(i))) {
				return i;
			}
		}
		return CommonConst.NOT_FOUND_INDEX;
	}

	public boolean containsEachChar(final String longOne, final String shortOne) {
		final int longLen = longOne.length();
		final int shortLen = shortOne.length();
		if (longLen < shortLen) {
			return false;
		}
		int index = 0;
		for (int i = 0; i < shortLen; ++i) {
			//较短者字符串里的每一个字符
			final char shortCh = shortOne.charAt(i);
			index = findCharIgnoreCase(shortCh, longOne, index, longLen);
			if (index == CommonConst.NOT_FOUND_INDEX || longLen - index < shortLen - i) {
				//若没在较长字符串里找到较短字符串里的这个字符
				//或者虽然找到了,但是较长字符串剩下的字符数小于较短字符串剩下的字符数
				return false;
			} else {
				//此时index是ch在较长字符串中所在的下标,下面准备比较短字符串的下一个字符.
				index += 1;
			}
		}
		return true;
	}

	//---------------------------------------------

	/**
	 * @author xuze(cantellow)
	 * @time Aug 25, 2011 3:13:47 PM
	 * @param
	 * @do 判断一个字符串是否是有0-9数字组成
	 * @Modify
	 */
	public boolean isNumeric(final String str) {
		if (isEmpty(str)) {
			return false;
		}
		final int size = str.length();
		for (int i = 0; i < size; i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	//===============================================

	private static final long serialVersionUID = 8134114489112738481L;

	private Object readResolve() {
		return INSTANCE;
	}

	//===============================================

	public static void main(final String... args) {
		System.out.println("@@@@@@" + Kits.STR.containsEachChar(" I~ loved you so much!!!", "i love you"));
		System.out.println("@@@@@@" + Kits.STR.indexAfter("我是谁", "是"));
		System.out.println("@@@@@@" + Kits.STR.subStringAfter("是不是啊", "是", 1, 4));
		System.out.println("@@@@@@" + Kits.STR.isNumeric("19384028a"));
		System.out.println("@@@@@@" + Kits.STR.startsWithIgnoreCase(" SELECT * FROM TABLE", " select * from table", true));
	}
}
