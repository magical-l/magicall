package me.magicall.net.util;

import me.magicall.consts.FileType;
import me.magicall.consts.SizeUnits;

public enum MetaType {
	JPG(SizeUnits.KB.toBytes(500)), //
	MP3(SizeUnits.MB.toBytes(5)), //
	GIF(SizeUnits.BYTE.toBytes(100)), //
	XML(SizeUnits.KB.toBytes(10)), //
	;
	public final FileType fileType;
	public final int possibleSize;

	private MetaType(final FileType fileType, final int possibleSize) {
		this.fileType = fileType;
		this.possibleSize = possibleSize;
	}

	private MetaType(final int possibleSize) {
		fileType = FileType.valueOf(name());
		this.possibleSize = possibleSize;
	}
}
