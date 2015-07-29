package me.magicall.net.http;

import java.net.MalformedURLException;
import java.net.URL;

public class Util {

	public static boolean isSuccess(final int statusCode) {
		return statusCode / 100 == 2;
	}

	public static String webRootDomain(final String url) {
		final String domain = domain(url);
		if (domain == null) {
			return null;
		}
		final int lastDot = domain.lastIndexOf('.');
		assert lastDot > 0;
		final int lastDot2 = domain.lastIndexOf('.', lastDot - 1);
		return domain.substring(lastDot2 + 1);
	}

	public static String domain(final String urlStr) {
		final URL url = url(urlStr);
		return url == null ? null : url.getHost();
	}

	public static URL url(final String url) {
		try {
			return new URL(url);
		} catch (final MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(final String... args) throws Exception {
		System.out.println(webRootDomain("http://www.baidu.com/abc.html"));
	}
}
