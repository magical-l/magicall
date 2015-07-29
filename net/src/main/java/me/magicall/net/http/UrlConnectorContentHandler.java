package me.magicall.net.http;

import java.net.HttpURLConnection;

public interface UrlConnectorContentHandler {

	void handleData(byte[] bs);

	void beforeConnect(HttpURLConnection conn);
}
