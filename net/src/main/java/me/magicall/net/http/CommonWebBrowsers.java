package me.magicall.net.http;


public enum CommonWebBrowsers implements RequestSender {

	/**
	 * FIXME:I DON'T HAVE IE6
	 */
	IE6("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; Trident/5.0; SLCC2; .NET CLR 2.0.50727;"//
			+ " .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C)"),
	/**
	 * I DON'T HAVE IE7,IT'S IE9
	 */
	IE7_("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; Trident/5.0; SLCC2; .NET CLR 2.0.50727;"//
			+ " .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C)"),
	/**
	 * 
	 */
	FIREFOX("Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.18) Gecko/20110614 Firefox/3.6.18"),
	/**
	 * 
	 */
	CHROME("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.52 Safari/536.5"),
	/**
	 * FIXME:I DON'T HAVE OPERA.IT'S CHROME
	 */
	OPERA("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.52 Safari/536.5"),
	/**
	 * FIXME:I DON'T HAVE SAFARI.IT'S CHROME
	 */
	SAFARI("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.52 Safari/536.5"), //
	;

	public final String userAgent;

	private CommonWebBrowsers(final String s) {
		userAgent = s;
	}

	@Override
	public String getUserAgent() {
		return userAgent;
	}
}
