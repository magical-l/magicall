package me.magicall.util;

import java.util.ArrayList;
import java.util.List;

import me.magicall.util.kit.Kits;

public enum LanguageLabelConverter {

	CAMEL {
		@Override
		public boolean isWordEdge(final char c) {
			return Character.isUpperCase(c);
		}

		@Override
		public void handleNextWordFirstChar(final StringBuilder sb, final char nextWordHeaderChar) {
			sb.append(Character.toUpperCase(nextWordHeaderChar));
		}

		@Override
		void handleFirstChar(final StringBuilder sb, final char firstChar) {
			sb.append(Character.toLowerCase(firstChar));
		}
	}, //
	SQL('_') {
		@Override
		void handleFirstChar(final StringBuilder sb, final char firstChar) {
			sb.append(Character.toLowerCase(firstChar));
		}
	}, //
	HTTP_REQUEST_HEADER('-') {
		@Override
		void handleFirstChar(final StringBuilder sb, final char firstChar) {
			sb.append(Character.toUpperCase(firstChar));
		}
	}, //
	;
	private final Character seperator;

	private LanguageLabelConverter() {
		this(null);
	}

	private LanguageLabelConverter(final Character seperator) {
		this.seperator = seperator;
	}

	boolean isWordEdge(final char c) {
		return c == getSeperator();
	}

	void handleNextWordFirstChar(final StringBuilder sb, final char nextWordHeaderChar) {
		sb.append(getSeperator());
		handleFirstChar(sb, nextWordHeaderChar);
	}

	void handleFirstChar(final StringBuilder sb, final char firstChar) {
		sb.append(firstChar);
	}

	public boolean hasSeperator() {
		return getSeperator() != null;
	}

	public String convertTo(final LanguageLabelConverter targetFormat, final String source) {
		if (Kits.STR.isEmpty(source)) {
			return source;
		}

		final StringBuilder sb = new StringBuilder();

		targetFormat.handleFirstChar(sb, source.charAt(0));

		final int len = source.length();
		for (int i = 1; i < len; ++i) {
			final char c = source.charAt(i);
			if (isWordEdge(c)) {
				targetFormat.handleNextWordFirstChar(sb, c);
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public String[] splitWords(final String s) {
		if (Kits.STR.isEmpty(s)) {
			return Kits.STR.emptyArray();
		}
		final List<String> list = new ArrayList<>();
		final int len = s.length();
		int lastIndex = 0;
		for (int i = 1; i < len; ++i) {
			final char c = s.charAt(i);
			if (isWordEdge(c)) {
				list.add(s.substring(lastIndex, i));

				if (hasSeperator()) {
					lastIndex = i + 1;
				} else {
					lastIndex = i;
				}
			}
		}
		list.add(s.substring(lastIndex));
		return list.toArray(new String[list.size()]);
	}

	public Character getSeperator() {
		return seperator;
	}

	public static void main(final String... args) {
		System.out.println("@@@@@@" + CAMEL.convertTo(SQL, "VisitLog"));
	}
}
