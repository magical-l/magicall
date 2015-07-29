package me.magicall.net.http;

import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import me.magicall.consts.SizeUnits;
import me.magicall.util.comparator.SerializableComparator;
import me.magicall.util.time.TimeUtil;
import me.magicall.util.touple.Tuple;
import me.magicall.util.touple.TwoTuple;

public class UrlConnectorConfig {
	private static final Comparator<TwoTuple<Charset, Float>> CHARSET_COMPARATOR = new SerializableComparator<TwoTuple<Charset, Float>>() {
		private static final long serialVersionUID = 473298899790255186L;

		@Override
		public int compare(final TwoTuple<Charset, Float> o1, final TwoTuple<Charset, Float> o2) {
			final float f1 = o1.second;
			final float f2 = o2.second;
			return f1 == f2 ? 0 : f1 > f2 ? -1 : 1;
		}
	};

	private String url;

//	private int possibleSize = SizeUnits.KB.toBytes();
	private int connectTimeout = (int) TimeUtil.MINUTE;
	private int readTimeout = (int) TimeUtil.MINUTE;
	private int readSteamEachSize = SizeUnits.KB.toBytes(4);

	private String cookies;
	private final RequestSender requestSender = CommonWebBrowsers.IE7_;
	private String acceptMetaType = "*/*";
	private String acceptLanguage = "zh-cn,zh;q=0.5";
	private String acceptEncoding = "gzip,deflate";

	private final SortedSet<TwoTuple<Charset, Float>> charsets = new TreeSet<>(CHARSET_COMPARATOR);
//	private String acceptCharset = "GB2312,utf-8;q=0.7,*;q=0.7";
	private String keepAlive = "100";
	private String headConnection = "keep-alive";

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

//	public void setMetaType(final MetaType metaType) {
//		setPossibleSize(metaType.possibleSize);
//	}
//
//	public int getPossibleSize() {
//		return possibleSize;
//	}
//
//	public void setPossibleSize(final int possibleSize) {
//		this.possibleSize = possibleSize;
//	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(final int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(final int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public int getReadSteamEachSize() {
		return readSteamEachSize;
	}

	public void setReadSteamEachSize(final int readSteamEachSize) {
		this.readSteamEachSize = readSteamEachSize;
	}

	public String getCookies() {
		return cookies;
	}

	public void setCookies(final String cookies) {
		this.cookies = cookies;
	}

	public String getAcceptMetaType() {
		return acceptMetaType;
	}

	public void setAcceptMetaType(final String acceptMetaType) {
		this.acceptMetaType = acceptMetaType;
	}

	public String getAcceptLanguage() {
		return acceptLanguage;
	}

	public void setAcceptLanguage(final String acceptLanguage) {
		this.acceptLanguage = acceptLanguage;
	}

	public String getAcceptEncoding() {
		return acceptEncoding;
	}

	public void setAcceptEncoding(final String acceptEncoding) {
		this.acceptEncoding = acceptEncoding;
	}

	public String getAcceptCharset() {
		if (charsets.isEmpty()) {
//			return "GBK,utf-8;q=0.7,*;q=0.7";
			return "*";
		}
		final StringBuilder sb = new StringBuilder();
		for (final TwoTuple<Charset, Float> t : charsets) {
			sb.append(t.first);
			if (t.second != 1) {
				sb.append(";q=").append(t.second);
			}
			sb.append(',');
		}
		return sb.deleteCharAt(sb.length() - 1).toString();
//		return acceptCharset;
	}

//	public void setAcceptCharset(final String acceptCharset) {
//		this.acceptCharset = acceptCharset;
//	}

	public String getKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(final String keepAlive) {
		this.keepAlive = keepAlive;
	}

	public String getHeadConnection() {
		return headConnection;
	}

	public void setHeadConnection(final String headConnection) {
		this.headConnection = headConnection;
	}

	public RequestSender getRequestSender() {
		return requestSender;
	}

	public Charset getFirstCharset() {
		return charsets.isEmpty() ? null : charsets.first().first;
	}

	public UrlConnectorConfig addCharset(final Charset charset, final float weight) {
		charsets.add(Tuple.of(charset, weight));
		return this;
	}

	public UrlConnectorConfig addCharset(final Charset charset) {
		charsets.add(Tuple.of(charset, 1f));
		return this;
	}
}
