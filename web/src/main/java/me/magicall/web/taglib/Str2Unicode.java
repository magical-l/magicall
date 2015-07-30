package me.magicall.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import me.magicall.util.kit.StrKit;

public class Str2Unicode extends SimpleTagSupport {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	@Override
	public void doTag() throws JspException, IOException {
		if (value != null) {
			getJspContext().getOut().write(StrKit.toUnicode(value));
		}
	}
}
