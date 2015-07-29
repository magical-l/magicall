package me.magicall.consts;

import java.util.regex.Pattern;

public interface RegexConst {

	String COMMON_SEPARATOR = "(,|\\s|/|，|、)+";

	Pattern ZIP_CODE = Pattern.compile("\\d{6}");
}
