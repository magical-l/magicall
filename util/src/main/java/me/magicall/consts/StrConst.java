package me.magicall.consts;

public interface StrConst {
	String EMPTY_STR = "";
	String[] EMPTY_STR_ARR = {};

	String y = "y";
	String Y = "Y";
	String yes = "yes";
	String YES = "YES";
	String n = "n";
	String N = "N";
	String no = "no";
	String NO = "NO";
	String ok = "ok";
	String OK = "OK";
	String 是 = "是";
	String 是的 = "是的";
	String 对 = "对";
	String 好 = "好";
	String 确定 = "确定";
	String ON = "on";

	String TRUE = Boolean.TRUE.toString();
	String FALSE = Boolean.FALSE.toString();
	String T = "T";
	String F = "F";
	String t = "t";
	String f = "f";

	String BOY = "男生";
	String GIRL = "女生";

	String GET = "get";
	int GET_LEN = GET.length();
	int GETTER_FIELD_NAME = GET_LEN + 1;

	String SET = "set";
	int SET_LEN = SET.length();
	int SETTER_FIELD_NAME = SET_LEN + 1;

	interface JsonConst {
		String EMPTY_OBJ = "{}";
		String EMPTY_ARR = "[]";
	}

	interface EncodingConst {
		String UTF8 = "utf8";//不区分大小写，而且可以加横杠。写成UTF-8、utf-8、utf8、UTF8都可以
		String GBK = "gbk";
		String ASCII = "ascii";
		String ISO8859_1 = "iso8859-1";
	}

	interface UrlConst {
		char IP_SEPERATOR_CHARACTER = '.';
		char URL_SEPERATOR_CHARACTER = '/';
		char URI_PARAMETER_SEPERATOR_CHAR = '?';
		char PARAMETER_SEPERATOR_CHAR = '&';
		String IP_SEPERATOR = String.valueOf(IP_SEPERATOR_CHARACTER);
		String URL_SEPERATOR = String.valueOf(URL_SEPERATOR_CHARACTER);
		String URI_PARAMETER_SEPERATOR = String.valueOf(URI_PARAMETER_SEPERATOR_CHAR);
		String PARAMETER_SEPERATOR = String.valueOf(PARAMETER_SEPERATOR_CHAR);

		String LOCALHOST = "localhost";
		String LOCALHOST_IP = "127.0.0.1";

		int HTTP_PORT = 80;

		String PROTOCOL_SEPARATOR = "://";

		String HTTP = "http";
		String HTTP_ = _U.protocolSeparator(HTTP);

		String HTTPS = "https";
		String HTTPS_ = _U.protocolSeparator(HTTPS);

		String FTP = "ftp";
		String FTP_ = _U.protocolSeparator(FTP);

	}

}
