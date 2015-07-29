package me.magicall.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import me.magicall.util.kit.Kits;

public class Href extends TagSupport {

	private static final long serialVersionUID = 164688653931059083L;

	private static final String JS = "javascript:";
	private static final String EMPTY_HREF = JS + ';';
	private static final String ABOUT_EMPTY_HREF = " href=\"" + EMPTY_HREF + "\" ";

	private String id;
	private String href;
	private boolean blank;
	private String css;
	private String js;
	private String other;
	private String style;
	private String alt;
	private String title;

	@Override
	public int doAfterBody() throws JspException {
		try {
			pageContext.getOut().write("</a>");
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		final StringBuilder sb = new StringBuilder("<a ");
		//href & onclick
		if (Kits.STR.isEmpty(href)) {
			sb.append(ABOUT_EMPTY_HREF);
		} else if (href.startsWith("javascript:") || href.startsWith("js:")) {
			sb.append(ABOUT_EMPTY_HREF).append("onclick=\"").append(JS).append(href.substring(href.indexOf(":") + 1)).append(";return false;\"");
		} else {//href
			sb.append("href=\"").append(href).append('"');
			//blank
			if (blank) {
				sb.append(' ').append("target=\"_blank\"");
			}
		}
		//id
		if (!Kits.STR.isEmpty(id)) {
			sb.append(' ').append("id=\"").append(id).append('"');
		}
		//class
		if (!Kits.STR.isEmpty(css)) {
			sb.append(' ').append("class=\"").append(css).append('"');
		}
		//other js
		if (!Kits.STR.isEmpty(js)) {
			sb.append(' ').append(js);
		}
		//style
		if (!Kits.STR.isEmpty(style)) {
			sb.append(' ').append("style=\"").append(style).append('"');
		}
		//alt
		if (!Kits.STR.isEmpty(alt)) {
			sb.append(' ').append("alt=\"").append(alt).append('"');
		}
		//title
		if (!Kits.STR.isEmpty(title)) {
			sb.append(' ').append("title=\"").append(title).append('"');
		}
		//other
		if (!Kits.STR.isEmpty(other)) {
			sb.append(' ').append(other);
		}
		//end
		sb.append(' ').append('>');//.append(content).append("</a>");
		final String string = sb.toString();
		try {
			pageContext.getOut().write(string);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

	public String getLink() {
		return href;
	}

	public void setLink(final String link) {
		href = link;
	}

	public boolean getBlank() {
		return blank;
	}

	public void setBlank(final boolean blank) {
		this.blank = blank;
	}

	public String getCss() {
		return css;
	}

	public void setCss(final String css) {
		this.css = css;
	}

	public String getJs() {
		return js;
	}

	public void setJs(final String js) {
		this.js = js;
	}

	public String getOther() {
		return other;
	}

	public void setOther(final String other) {
		this.other = other;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(final String style) {
		this.style = style;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(final String id) {
		this.id = id;
	}

	public String getHref() {
		return href;
	}

	public void setHref(final String href) {
		this.href = href;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(final String alt) {
		this.alt = alt;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

}
