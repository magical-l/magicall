package me.magicall.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import me.magicall.consts.DataType;
import me.magicall.consts.FileType;
import me.magicall.util.kit.Kits;

public class ShowUrlResourceTag extends SimpleTagSupport {

	private String url;

	@Override
	public void doTag() throws JspException, IOException {
		if (!Kits.STR.isEmpty(url)) {
			final FileType fileType = FileType.getByFileName(url);
			if (fileType != null) {
				final DataType dataType = fileType.getDataType();
				if (dataType == DataType.IMG) {//FIXME:now only supports imgs
					getJspContext().getOut().write("<img src='" + url + "'/>");
				}
			}
		}
	}

	public void setValue(final String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

}
