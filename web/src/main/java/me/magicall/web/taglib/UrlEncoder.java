package me.magicall.web.taglib;

import me.magicall.consts.StrConst.EncodingConst;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlEncoder extends SimpleTagSupport {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	@Override
	public void doTag() throws JspException, IOException, UnsupportedEncodingException {
		if (value != null) {
			getJspContext().getOut().write(URLEncoder.encode(value, EncodingConst.UTF8));
		}
	}
}
