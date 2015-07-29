package me.magicall.consts;

import me.magicall.consts.StrConst.UrlConst;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

class _U {
	static final Date NEW_YEAR_DAY_OF_0;
	static {
		Date d;
		try {
			d = new SimpleDateFormat("yMd").parse("111");
		} catch (final ParseException e) {
			d = null;
		}
		NEW_YEAR_DAY_OF_0 = d;
	}

	private static final int[] month_days_of_common_year = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	static final List<Integer> MONTH_DAYS_OF_COMMON_YEAR;
	static {
		final List<Integer> l1 = new ArrayList<>(month_days_of_common_year.length);
		for (final int i : month_days_of_common_year) {
			l1.add(i);
		}
		MONTH_DAYS_OF_COMMON_YEAR = Collections.unmodifiableList(l1);
	}

	private static final int[] month_days_of_leap_year = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	static final List<Integer> MONTH_DAYS_OF_LEAP_YEAR;

	static {
		final List<Integer> l2 = new ArrayList<>(month_days_of_leap_year.length);
		for (final int i : month_days_of_leap_year) {
			l2.add(i);
		}
		MONTH_DAYS_OF_LEAP_YEAR = Collections.unmodifiableList(l2);
	}

	static String protocolSeparator(final String protocolName) {
		return protocolName + UrlConst.PROTOCOL_SEPARATOR;
	}

	public static void main(final String... args) throws ParseException {
		System.out.println("@@@@@@_U.main():" + new SimpleDateFormat("yyyyMMdd").parse("2013101"));
	}
}
