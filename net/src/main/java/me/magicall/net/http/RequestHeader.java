package me.magicall.net.http;

import java.net.HttpURLConnection;

import javax.servlet.http.HttpServletRequest;

public enum RequestHeader {
	Accept, //
	Accept_Encoding, //
	Accept_Language, //
	Accept_Charset, //
	Cache_Control, //
	Connection, //
	Cookie, //
	Host, //
	Keep_Alive, //
	Range, //
	Referer, //
	User_Agent, //
	;

	private final String headName;

	private RequestHeader() {
		headName = name().replace('_', '-');
	}

	public String getValue(final HttpServletRequest request) {
		return request.getHeader(getHeadName());
	}

	public String getHeadName() {
		return headName;
	}

	public void set(final HttpURLConnection conn, final Object value) {
		conn.setRequestProperty(headName, String.valueOf(value));
	}
}
