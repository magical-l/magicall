package me.magicall.consts;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public interface TimeConst {

	interface YearConst {
		/**
		 * java日历的时间原点的年份
		 */
		int START_YEAR = 1970;
		/**
		 * java日历中时间原点后的第一个闰年
		 */
		int FIRST_LEAP_YEAR = 1972;
	}

	interface MsConst {

		long SECOND = TimeUnit.SECONDS.toMillis(1);
		long MINUTE = TimeUnit.MINUTES.toMillis(1);
		long HOUR = TimeUnit.HOURS.toMillis(1);
		/**
		 * 8小时的毫秒数.用于时区计算.中国是+8时区.
		 */
		long HOUR8 = TimeUnit.HOURS.toMillis(8);
		/**
		 * 16小时的毫秒数.用于时区计算.中国是+8时区,对24的补数是16.
		 */
		long HOUR16 = TimeUnit.HOURS.toMillis(16);
		long DATE = TimeUnit.DAYS.toMillis(1);
		long DAY = DATE;
		long WEEK = TimeUnit.DAYS.toMillis(7);
		long DAY3 = TimeUnit.DAYS.toMillis(3);
		long DAY7 = WEEK;
		long DAY28 = TimeUnit.DAYS.toMillis(28);
		long DAY29 = TimeUnit.DAYS.toMillis(29);
		long DAY30 = TimeUnit.DAYS.toMillis(30);
		long DAY31 = TimeUnit.DAYS.toMillis(31);
		long DAY365 = TimeUnit.DAYS.toMillis(365);
		long DAY366 = TimeUnit.DAYS.toMillis(366);
		long YEAR_COMMON = DAY365;
		long YEAR_LEAP = DAY366;

		/**
		 * 普通连续4年的毫秒总数，注意其中有一年是闰年
		 */
		long COMMON_YEAR4 = YEAR_COMMON * 3 + YEAR_LEAP;
	}

	interface DateConst {

		/**
		 * 公元元年元旦
		 */
		Date NEW_YEAR_DAY_OF_0 = _U.NEW_YEAR_DAY_OF_0;
		/**
		 * java long型能表示的最小时间
		 */
		Date START = new Date(Long.MIN_VALUE);
		/**
		 * 0毫秒的时间
		 */
		Date _0 = new Date(0);
		/**
		 * 0毫秒的时间
		 */
		Date _1970_1_1 = _0;
		/**
		 * java long型能表示的最大时间
		 */
		Date END = new Date(Long.MAX_VALUE);

		List<Integer> MONTH_DAYS_OF_COMMON_YEAR = _U.MONTH_DAYS_OF_COMMON_YEAR;
		List<Integer> MONTH_DAYS_OF_LEAP_YEAR = _U.MONTH_DAYS_OF_LEAP_YEAR;
	}
}
