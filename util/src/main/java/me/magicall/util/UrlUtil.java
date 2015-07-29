package me.magicall.util;

import me.magicall.consts.StrConst.UrlConst;

public class UrlUtil implements UrlConst {
	public static boolean isRootPath(final String url) {
		return url.startsWith(UrlConst.URL_SEPERATOR);
	}
}
