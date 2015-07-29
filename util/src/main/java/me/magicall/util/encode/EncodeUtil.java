package me.magicall.util.encode;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EncodeUtil {

	public static String encode(final String source) {
		return encode(source, Encodes.UTF8);
	}

	public static String encode(final String source, final Encodes encode) {
		try {
			return URLEncoder.encode(source, encode.encode);
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param args
	 */
	public static void main(final String... args) {

	}

}
