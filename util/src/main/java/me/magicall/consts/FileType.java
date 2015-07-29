package me.magicall.consts;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

public enum FileType implements FilenameFilter, FileFilter {
	JPG(DataType.IMG, "jpeg"), //
	GIF(DataType.IMG), //
	BMP(DataType.IMG), //
	ICO(DataType.IMG), //
	PNG(DataType.IMG), //

	TXT(DataType.TEXT), //
	XML(DataType.TEXT), //
	HTML(DataType.TEXT, "htm"), //
	PROPERTIES(DataType.TEXT), //
	JSP(DataType.TEXT), //
	JS(DataType.TEXT), //
	ASP(DataType.TEXT), //
	PHP(DataType.TEXT), //
	PY(DataType.TEXT), //

	WAV(DataType.AUDIO), //
	MP3(DataType.AUDIO), //

	MP4(DataType.VIDEO), //
	RMVB(DataType.VIDEO), //

	BIN(DataType.BINARY), //

	;

	private static Map<String, FileType> map = new HashMap<>();
	static {
		for (final FileType t : values()) {
			for (final String s : t.suffixes) {
				map.put(s, t);
			}
		}
	}

	public static String getNameWithoutSuffix(final File file) {
		final String fileName = file.getName();
		final int lastIndex = fileName.lastIndexOf('.');
		return lastIndex == CommonConst.NOT_FOUND_INDEX ? fileName : fileName.substring(0, lastIndex);
	}

	public static FileType getByFileName(final String fileName) {
		if (fileName.indexOf('.') >= 0) {
			return map.get(fileName.substring(fileName.lastIndexOf('.')).toLowerCase());
		}
		return null;
	}

	private final DataType dataType;
	private final String[] suffixes;

	private FileType(final DataType dataType, final String... otherSuffixes) {
		this.dataType = dataType;
		suffixes = new String[1 + otherSuffixes.length];
		suffixes[0] = '.' + name().toLowerCase();
		for (int i = 0; i < otherSuffixes.length; ++i) {
			suffixes[i + 1] = '.' + otherSuffixes[i].toLowerCase();
		}
	}

	public DataType getDataType() {
		return dataType;
	}

	public String[] getSuffixes() {
		return suffixes.clone();
	}

	@Override
	public boolean accept(final File dir, final String name) {
		for (final String suffix : suffixes) {
			if (name.endsWith(suffix)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean accept(final File file) {
		return accept(null, file.getName());
	}

}
