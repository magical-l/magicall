package me.magicall.net.http;

import java.net.HttpURLConnection;
import java.nio.charset.Charset;

public class HtmlGetter {

	private final UrlConnectorConfig config;

	public HtmlGetter(final UrlConnectorConfig config) {
		super();
		this.config = config;
	}

	public String get() {
		final UrlConnector connector = new UrlConnector(config);
		final NetStreamToString toString = new NetStreamToString();
		connector.add(toString);
		connector.downBytes();
		return toString.content;
	}

	private class NetStreamToString implements UrlConnectorContentHandler {
		private String content;

		@Override
		public void beforeConnect(final HttpURLConnection conn) {
		}

		@Override
		public void handleData(final byte[] bs) {
			final Charset charset = config.getFirstCharset();
//			System.out.println("@@@@@@HtmlGetter.NetStreamToString.handleData():" + Arrays.toString(bs));
//			try {
//				System.out.println("@@@@@@HtmlGetter.NetStreamToString.handleData():" + new String(bs, "utf8"));
//				System.out.println("@@@@@@HtmlGetter.NetStreamToString.handleData():" + new String(bs, "GBK"));
//			} catch (final UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
			content = charset == null ? new String(bs) : new String(bs, charset);
		}
	}
}
