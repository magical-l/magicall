package me.magicall.net.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import me.magicall.convenient.BaseHasList;
import me.magicall.util.kit.Kits;

public class UrlConnector extends BaseHasList<UrlConnectorContentHandler> {

	private final UrlConnectorConfig config;

	public UrlConnector(final UrlConnectorConfig config) {
		this.config = config;
	}

	public void downBytes() {
		final String url = config.getUrl();
		InputStream in = null;
		try {
			final HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			buildConnection(url, conn);

			final List<UrlConnectorContentHandler> handlers = getUrlConnectorContentHandlers();
			for (final UrlConnectorContentHandler handler : handlers) {
				handler.beforeConnect(conn);
			}

			conn.connect();

			final int code = conn.getResponseCode();
			if (Util.isSuccess(code)) {
				in = conn.getInputStream();
				final int contentLen = conn.getContentLength();

				int readSteamEachSize = config.getReadSteamEachSize();
				final String contentRange = conn.getHeaderField("Content-Range");
				if (contentRange != null) {
					final int fetchedTotalBytes = Kits.INT.fromString(Kits.STR.subStringAfter(contentRange, "/"));
					if (readSteamEachSize != fetchedTotalBytes) {
						readSteamEachSize = fetchedTotalBytes;
					}
				}

				final ByteArrayOutputStream byteArrayOutputStream = contentLen < 0 ? new ByteArrayOutputStream() : new ByteArrayOutputStream(contentLen);
				final byte[] tmp = new byte[readSteamEachSize];
				in.read(tmp);
				for (int r = in.read(tmp); r != -1; r = in.read(tmp)) {
					byteArrayOutputStream.write(tmp, 0, r);
				}
				conn.disconnect();

				final byte[] bs = byteArrayOutputStream.toByteArray();
				for (final UrlConnectorContentHandler handler : handlers) {
					handler.handleData(bs);
				}
			} else {
			}
		} catch (final MalformedURLException e) {
			e.printStackTrace();
		} catch (final ProtocolException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void buildConnection(final String url, final HttpURLConnection conn) throws ProtocolException {
		final String host = Kits.STR.middle(url, "http://", false, "/", false);
		final String reffer = Kits.STR.subStringBefore(url, "/", true);

		conn.setRequestMethod("GET");

		//请求头
		RequestHeader.Host.set(conn, host);
		RequestHeader.User_Agent.set(conn, config.getRequestSender().getUserAgent());

		final String acceptLanguage = config.getAcceptLanguage();
		if (!Kits.STR.isEmpty(acceptLanguage)) {
			RequestHeader.Accept_Language.set(conn, acceptLanguage);
		}

		final String acceptEncoding = config.getAcceptEncoding();
		if (!Kits.STR.isEmpty(acceptEncoding)) {
			RequestHeader.Accept_Encoding.set(conn, acceptEncoding);
		}

		final String acceptCharset = config.getAcceptCharset();
		if (!Kits.STR.isEmpty(acceptCharset)) {
			RequestHeader.Accept_Charset.set(conn, acceptCharset);
		}

		final String keepAlive = config.getKeepAlive();
		if (!Kits.STR.isEmpty(keepAlive)) {
			RequestHeader.Keep_Alive.set(conn, keepAlive);
		}

		final String headConnection = config.getHeadConnection();
		if (!Kits.STR.isEmpty(headConnection)) {
			RequestHeader.Connection.set(conn, headConnection);
		}
		RequestHeader.Referer.set(conn, reffer);

		conn.setConnectTimeout(config.getConnectTimeout());
		conn.setReadTimeout(config.getReadTimeout());
	}

	protected List<UrlConnectorContentHandler> getUrlConnectorContentHandlers() {
		return getList();
	}
}
