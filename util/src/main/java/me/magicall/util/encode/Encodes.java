package me.magicall.util.encode;

public enum Encodes {

	UTF8, GBK, GB2312, ISO8859_1, ASCII;
	public final String encode;

	private Encodes(final String encode) {
		this.encode = encode;
	}

	private Encodes() {
		encode = name();
	}

	public String getEncode() {
		return encode;
	}

}
