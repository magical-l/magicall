package me.magicall.consts;

public interface StrConst {
	public static final String EMPTY_STR = "";
	public static final String[] EMPTY_STR_ARR = {};

	public static final String y = "y";
	public static final String Y = "Y";
	public static final String yes = "yes";
	public static final String YES = "YES";
	public static final String n = "n";
	public static final String N = "N";
	public static final String no = "no";
	public static final String NO = "NO";
	public static final String ok = "ok";
	public static final String OK = "OK";
	public static final String 是 = "是";
	public static final String 是的 = "是的";
	public static final String 对 = "对";
	public static final String 好 = "好";
	public static final String 确定 = "确定";
	public static final String ON = "on";

	public static final String TRUE = Boolean.TRUE.toString();
	public static final String FALSE = Boolean.FALSE.toString();
	public static final String T = "T";
	public static final String F = "F";
	public static final String t = "t";
	public static final String f = "f";

	public static final String BOY = "男生";
	public static final String GIRL = "女生";

	String GET = "get";
	int GET_LEN = GET.length();
	int GETTER_FIELD_NAME = GET_LEN + 1;

	String SET = "set";
	int SET_LEN = SET.length();
	int SETTER_FIELD_NAME = SET_LEN + 1;

	public interface JsonConst {
		public static final String EMPTY_OBJ = "{}";
		public static final String EMPTY_ARR = "[]";
	}

	public interface EncodingConst {
		public static final String UTF8 = "utf8";//不区分大小写，而且可以加横杠。写成UTF-8、utf-8、utf8、UTF8都可以
		public static final String GBK = "gbk";
		public static final String ASCII = "ascii";
		public static final String ISO8859_1 = "iso8859-1";
	}

	public interface UrlConst {
		public static final char IP_SEPERATOR_CHARACTER = '.';
		public static final char URL_SEPERATOR_CHARACTER = '/';
		public static final char URI_PARAMETER_SEPERATOR_CHAR = '?';
		public static final char PARAMETER_SEPERATOR_CHAR = '&';
		public static final String IP_SEPERATOR = String.valueOf(IP_SEPERATOR_CHARACTER);
		public static final String URL_SEPERATOR = String.valueOf(URL_SEPERATOR_CHARACTER);
		public static final String URI_PARAMETER_SEPERATOR = String.valueOf(URI_PARAMETER_SEPERATOR_CHAR);
		public static final String PARAMETER_SEPERATOR = String.valueOf(PARAMETER_SEPERATOR_CHAR);

		public static final String LOCALHOST = "localhost";
		public static final String LOCALHOST_IP = "127.0.0.1";

		public static final int HTTP_PORT = 80;

		public static final String PROTOCOL_SEPARATOR = "://";

		public static final String HTTP = "http";
		public static final String HTTP_ = _U.protocolSeparator(HTTP);

		public static final String HTTPS = "https";
		public static final String HTTPS_ = _U.protocolSeparator(HTTPS);

		public static final String FTP = "ftp";
		public static final String FTP_ = _U.protocolSeparator(FTP);

	}

}
