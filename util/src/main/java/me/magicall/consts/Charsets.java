package me.magicall.consts;

import java.nio.charset.Charset;

import me.magicall.consts.StrConst.EncodingConst;

public interface Charsets {

	Charset GBK = Charset.forName(EncodingConst.GBK);
	Charset UTF8 = Charset.forName(EncodingConst.UTF8);
}
