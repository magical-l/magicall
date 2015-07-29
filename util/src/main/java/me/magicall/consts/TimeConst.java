package me.magicall.consts;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public interface TimeConst {

	public interface YearConst {
		/**
		 * java日历的时间原点的年份
		 */
		public static final int START_YEAR = 1970;
		/**
		 * java日历中时间原点后的第一个闰年
		 */
		public static final int FIRST_LEAP_YEAR = 1972;
	}

	public interface MsConst {

		public static final long SECOND = TimeUnit.SECONDS.toMillis(1);
		public static final long MINUTE = TimeUnit.MINUTES.toMillis(1);
		public static final long HOUR = TimeUnit.HOURS.toMillis(1);
		/**
		 * 8小时的毫秒数.用于时区计算.中国是+8时区.
		 */
		public static final long HOUR8 = TimeUnit.HOURS.toMillis(8);
		/**
		 * 16小时的毫秒数.用于时区计算.中国是+8时区,对24的补数是16.
		 */
		public static final long HOUR16 = TimeUnit.HOURS.toMillis(16);
		public static final long DATE = TimeUnit.DAYS.toMillis(1);
		public static final long DAY = DATE;
		public static final long WEEK = TimeUnit.DAYS.toMillis(7);
		public static final long DAY3 = TimeUnit.DAYS.toMillis(3);
		public static final long DAY7 = WEEK;
		public static final long DAY28 = TimeUnit.DAYS.toMillis(28);
		public static final long DAY29 = TimeUnit.DAYS.toMillis(29);
		public static final long DAY30 = TimeUnit.DAYS.toMillis(30);
		public static final long DAY31 = TimeUnit.DAYS.toMillis(31);
		public static final long DAY365 = TimeUnit.DAYS.toMillis(365);
		public static final long DAY366 = TimeUnit.DAYS.toMillis(366);
		public static final long YEAR_COMMON = DAY365;
		public static final long YEAR_LEAP = DAY366;

		/**
		 * 普通连续4年的毫秒总数，注意其中有一年是闰年
		 */
		public static final long COMMON_YEAR4 = YEAR_COMMON * 3 + YEAR_LEAP;
	}

	public interface DateConst {

		/**
		 * 公元元年元旦
		 */
		public static final Date NEW_YEAR_DAY_OF_0 = _U.NEW_YEAR_DAY_OF_0;
		/**
		 * java long型能表示的最小时间
		 */
		public static final Date START = new Date(Long.MIN_VALUE);
		/**
		 * 0毫秒的时间
		 */
		public static final Date _0 = new Date(0);
		/**
		 * 0毫秒的时间
		 */
		public static final Date _1970_1_1 = _0;
		/**
		 * java long型能表示的最大时间
		 */
		public static final Date END = new Date(Long.MAX_VALUE);

		public static final List<Integer> MONTH_DAYS_OF_COMMON_YEAR = _U.MONTH_DAYS_OF_COMMON_YEAR;
		public static final List<Integer> MONTH_DAYS_OF_LEAP_YEAR = _U.MONTH_DAYS_OF_LEAP_YEAR;
	}
}
