package me.magicall.web.taglib;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import me.magicall.util.kit.Kits;
import me.magicall.util.time.TimeFormatter;
import me.magicall.util.time.TimeUtil;

public class ContentTag extends SimpleTagSupport {

	private static final Pattern KEYS_SPLIT_PATTERN = Pattern.compile("([,;，；、]|\\s)+");
//	private static Pattern URL=Pattern.compile("http://[a-zA-Z0-9\\?\\./\\+~-=&]");

	private static final Map<String, String> replacementMap = new LinkedHashMap<>();

	static {
		replacementMap.put("&", "&amp;");//must first
		replacementMap.put(" ", "&nbsp;");
		replacementMap.put("'", "&apos;");
		replacementMap.put("\"", "&quot;");
		replacementMap.put("\t", "&ensp;");
		replacementMap.put("<", "&lt;");
		replacementMap.put(">", "&gt;");
		replacementMap.put("(\r|\n|\r\n)", "<br/>");
		replacementMap.put("×", "&times;");
		replacementMap.put("÷", "&divide;");
		replacementMap.put("©", "&copy;");
		replacementMap.put("®", "&reg;");
		replacementMap.put("™", "&#8482;");
	}

	private Object value;
	private String keys;

	@Override
	public void doTag() throws JspException, IOException {
		if (value != null) {
			final String s;
			if (value instanceof Date) {
				s = handleDate();
			} else if (value instanceof String) {
				s = handleString();
			} else {
				s = value.toString();
			}

			if (!Kits.STR.isEmpty(s)) {
				try {
					getJspContext().getOut().write(s);
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String handleString() {
		String s = (String) value;

		if (!Kits.STR.isEmpty(keys)) {
			final String[] kiss = KEYS_SPLIT_PATTERN.split(keys);
			for (final String t : kiss) {
				final String replacement = replacementMap.get(t);
				if (replacement != null) {
					s = s.replace(t, replacement);
				}
			}
		}
		return s;
	}

	private String handleDate() {
		if (keys == null) {
			return TimeFormatter.Y4_M2_D2_H2_MIN2_S2.format((Date) value);
		} else {
			return TimeUtil.format(keys, (Date) value);
		}
	}

	public Object getValue() {
		return value;
	}

	public void setValue(final Object value) {
		this.value = value;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(final String keys) {
		this.keys = keys;
	}

}
