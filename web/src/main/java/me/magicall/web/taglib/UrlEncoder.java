package me.magicall.web.taglib;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import me.magicall.consts.StrConst.EncodingConst;

public class UrlEncoder extends SimpleTagSupport {

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
			super.getJspContext().getOut().write(URLEncoder.encode(value, EncodingConst.UTF8));
		}
	}
}
