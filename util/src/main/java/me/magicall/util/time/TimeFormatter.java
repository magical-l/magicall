/**
 * 
 */
package me.magicall.util.time;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public enum TimeFormatter {
	// 以下排序,是按使用率从高到低排列.保证parse的时候能最快地遇到合适的pattern出来

	/**
	 * yyyy-MM-dd HH:mm:ss.SSS
	 */
	Y4_M2_D2_H2_MIN2_S2_MS3("yyyy-MM-dd HH:mm:ss.SSS"), //
	/**
	 * 按照url格式的日期,其中日期和时间中间的空格被改成+ yyyy-MM-dd+HH:mm:ss
	 */
	Y4_M2_D2_H2_MIN2_S2_URL("yyyy-MM-dd+HH:mm:ss"), //
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	Y4_M2_D2_H2_MIN2_S2("yyyy-MM-dd HH:mm:ss"), //
	/**
	 * 年(2位)-月-日 时:分:秒.从00到29解析为20xx年,从30到99解析为19xx年 这个也可以解析 yyyy-MM-dd
	 * HH:mm:ss
	 */
	Y2_M2_D2_H2_MIN2_S2("yy-MM-dd HH:mm:ss"), //
	/**
	 * yyyy-MM-dd HH:mm
	 */
	Y4_M2_D2_H2_M2("yyyy-MM-dd HH:mm"), //
	/**
	 * yy-MM-dd HH:mm
	 */
	Y2_M2_D2_H2_MIN2("yy-MM-dd HH:mm"), //
	/**
	 * yyyy-MM-dd
	 */
	Y4_M2_D2("yyyy-MM-dd"), //
	/**
	 * yyyyMMddHHmmssSSS
	 */
	Y4M2D2H2MIN2S2MS3("yyyyMMddHHmmssSSS"), //
	/**
	 * yyyyMMdd
	 */
	Y4M2D2("yyyyMMdd"), //
	/**
	 * yyyy年MM月dd日
	 */
	Y4年M2月D2日("yyyy年MM月dd日"), //
	Y4年M2月D2日H2时M2分S2秒("yyyy年MM月dd日HH时mm分ss秒"),
	/**
	 * MM-dd
	 */
	M2_D2("MM-dd"), //
	/**
	 * yyyy-MM
	 */
	Y4_M2("yyyy-MM"), //
	/**
	 * HH:mm
	 */
	H2_MIN2("HH:mm"), //
	/**
	 * M.d
	 */
	M_D("M.d"), //
	/**
	 * HHmm
	 */
	H2MIN2("HHmm"), //
	/**
	 * yyyy-MM-dd-HH-mm-ss log4j默认格式
	 */
	Y4_M2_D2_H2_m2_s2("yyyy-MM-dd-HH-mm-ss"), //
	M("M"), //
	;
	private final ThreadLocal<WeakReference<SimpleDateFormat>> simpleDateFormatHolder = new ThreadLocal<>();
	public final String pattern;

	private TimeFormatter(final String pattern) {
		this.pattern = pattern;
	}

	public String format(final Date date) {
		return getFormat().format(date);
	}

	public Date parse(final String s) {
		try {
			return getFormat().parse(s);
		} catch (final ParseException e) {
			return null;
		}
	}

	public Date parse(final String s, final ParsePosition pos) {
		return getFormat().parse(s, pos);
	}

	private SimpleDateFormat getFormat() {
		final WeakReference<SimpleDateFormat> ref = simpleDateFormatHolder.get();
		SimpleDateFormat format;
		if (ref == null) {
			format = init();
		} else {
			format = ref.get();
			if (format == null) {
				format = init();
			}
		}
		return format;
	}

	private SimpleDateFormat init() {
		final SimpleDateFormat format = new SimpleDateFormat(pattern);
		simpleDateFormatHolder.set(new WeakReference<>(format));
		return format;
	}

	@Override
	public String toString() {
		return pattern;
	}

	public static String tryToFormat(final Date date) {
		final TimeFormatter[] formatters = values();
		for (final TimeFormatter f : formatters) {
			try {
				return f.format(date);
			} catch (final Exception e) {
				continue;
			}
		}
		return date.toString();
	}

	public static Date tryToParse(final String s, final ParsePosition pos) {
		final int index = pos.getIndex();
		final TimeFormatter[] formatters = values();
		for (final TimeFormatter f : formatters) {
			final Date date = f.parse(s, pos);
			if (date == null) {
				pos.setIndex(index);
			} else {
				return date;
			}
		}
		return null;
	}

	public static Date tryToParse(final String s) {
		final TimeFormatter[] formatters = values();
		for (final TimeFormatter f : formatters) {
			final Date date = f.parse(s, new ParsePosition(0));
			if (date != null) {
				return date;
			}
		}
		return null;
	}
}