package me.magicall.util.time;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.magicall.consts.TimeConst.YearConst;
import me.magicall.util.kit.Kits;

public class TimeUtil {

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
	public static final long DAY7 = WEEK;
	public static final long DAY28 = TimeUnit.DAYS.toMillis(28);
	public static final long DAY29 = TimeUnit.DAYS.toMillis(29);
	public static final long DAY30 = TimeUnit.DAYS.toMillis(30);
	public static final long DAY31 = TimeUnit.DAYS.toMillis(31);
	public static final long DAY365 = TimeUnit.DAYS.toMillis(365);
	public static final long DAY366 = TimeUnit.DAYS.toMillis(366);
	public static final long YEAR_COMMON = DAY365;
	public static final long YEAR2 = YEAR_COMMON * 2;
	public static final long YEAR_LEAP = DAY366;

	public static final long YEAR4 = YEAR_COMMON * 3 + YEAR_LEAP;

	public static final long LEAP_DAY_START_IN_4Y = YEAR_COMMON * 2 + DAY31 + DAY28;

	public static final long LEAP_DAY_END_IN_4Y = YEAR_COMMON * 2 + DAY31 + DAY29;

	private static final int[] common_year_days_by_month = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	public static final List<Integer> COMMON_YEAR_DAYS_BY_MONTH = Kits.INT.asList(common_year_days_by_month);

	private static final int[] leap_year_days_by_month = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	public static final List<Integer> LEAP_YEAR_DAYS_BY_MONTH = Kits.INT.asList(leap_year_days_by_month);

	public static Calendar dateStart(final Calendar c) {
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}

	public static long dateStart(final Date date) {
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis();
	}

	public static String format(final String type, final Date date) {
		return new SimpleDateFormat(type).format(date);
	}

//	public static String format(final Date date) {
//		return format(defaultType, date);
//	}

	public static String format(final String type, final long date) {
		return format(type, new Date(date));
	}

//	public static String format(final long date) {
//		return format(defaultType, new Date(date));
//	}

	public static Date parse(final String type, final String s) {
		if (Kits.STR.isEmpty(s)) {
			return null;
		}
		return new SimpleDateFormat(type).parse(s.trim(), new ParsePosition(0));
	}

	public static Date parse(String s) {
		if (Kits.STR.isEmpty(s)) {
			return null;
		}
		s = s.trim();
		// return parse(defaultType, s);
		// SimpleDateFormat每次都需要new,因为其不是线程安全的!
		final SimpleDateFormat format = new SimpleDateFormat();
		for (final TimeFormatter f : TimeFormatter.values()) {
			// ParsePosition每次都要new,因为它在parse方法里会被改变!
			format.applyPattern(f.pattern);
			final Date d = format.parse(s, new ParsePosition(0));
			if (d != null) {
				return d;
			}
		}
		return null;
	}

	public static Date getNow() {
		return new Date();
	}

	public static boolean afterNow(final Date d) {
		return d.after(new Date());
	}

	public static boolean afterNow(final Timestamp d) {
		return d.after(new Date());
	}

	public static boolean beforeNow(final Date d) {
		return d.before(new Date());
	}

	public static boolean beforeNow(final Timestamp d) {
		return d.before(new Date());
	}

	public static Date getDateTimeAfter(final Date _from, final int _time_type, final int _count) {
		final Calendar c = Calendar.getInstance();
		if (_from != null) {
			c.setTime(_from);
		}
		c.add(_time_type, _count);
		return c.getTime();
	}

	public static Date getDateBefore(final Date day) {
		final Calendar c = Calendar.getInstance();
		c.setTime(day);
		final int day1 = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day1 - 1);

		return c.getTime();
	}

	/**
	 * <pre>
	 * 返回指定时间的某天之后的时间(当前时刻).
	 * </pre>
	 * 
	 * @param _from
	 *            null表示当前
	 * @param _days
	 *            可以为负值
	 * @return
	 */
	public static Date getDateAfter(final Date _from, final int _days) {
		return getDateTimeAfter(_from, Calendar.DAY_OF_MONTH, _days);
	}

	/**
	 * <pre>
	 * 返回指定时间的某月之后的时间(当前时刻).
	 * </pre>
	 * 
	 * @param _from
	 *            null表示当前
	 * @param _mons
	 *            可以为负值
	 * @return
	 */
	public static Date getMonthAfter(final Date _from, final int _mons) {
		return getDateTimeAfter(_from, Calendar.MONTH, _mons);
	}

	/**
	 * <pre>
	 * 返回指定时间的某小时之后的时间(当前时刻).
	 * </pre>
	 * 
	 * @param _from
	 *            null表示当前
	 * @param _hours
	 *            可以为负值
	 * @return
	 */
	public static Date getHourAfter(final Date _from, final int _hours) {
		return getDateTimeAfter(_from, Calendar.HOUR_OF_DAY, _hours);
	}

	/**
	 * <pre>
	 * 返回指定时间的某毫秒之后的时间(当前时刻).
	 * </pre>
	 * 
	 * @param _from
	 *            null表示当前
	 * @param _milliseconds
	 *            可以为负值
	 * @return
	 */
	public static Date getMillisecondAfter(final Date _from, final int _milliseconds) {
		return getDateTimeAfter(_from, Calendar.MILLISECOND, _milliseconds);
	}

	/**
	 * <pre>
	 * 判断时间是否在两个时间之间
	 * </pre>
	 * 
	 * @param time
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean timeIsIn(final Date time, final Date start, final Date end) {
		return time.after(start) && time.before(end);
	}

	/**
	 * <pre>
	 * 判断现在是否在两个时间之间
	 * </pre>
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean nowIsIn(final Date start, final Date end) {
		return timeIsIn(new Date(), start, end);
	}

	/**
	 * 判断两个时间是否在同一天内.如果escapeYear为true,则不计较年份.可用于节日,生日等每年都有的日子
	 * 
	 * @param day1
	 * @param day2
	 * @param escapeYear
	 *            如果escapeYear为true,则不计较年份
	 * @return
	 */
	public static boolean isTheSameDay(final Date day1, final Date day2, final boolean escapeYear) {
		return isTheSameDay(day1, day2, escapeYear, true);
	}

	public static boolean isTheSameDay(final Date day1, final Date day2, final boolean escapeYear, final boolean checkSummerTime) {
		return checkSummerTime ? isTheSameDayCheckSummerTime(day1, day2, escapeYear) : isTheSameDayNoSummerTimeCheck(day1, day2, escapeYear);
	}

	private static boolean isTheSameDayCheckSummerTime(final Date day1, final Date day2, final boolean escapeYear) {
		final Calendar c = Calendar.getInstance();
		c.setTime(day1);
		final int y = c.get(Calendar.YEAR);
		final int m = c.get(Calendar.MONTH);
		final int d = c.get(Calendar.DATE);
		c.setTime(day2);
		return (escapeYear || y == c.get(Calendar.YEAR)) && m == c.get(Calendar.MONTH) && d == c.get(Calendar.DATE);
	}

	/**
	 * 已验证从0L毫秒起的4万天正确.1970年以前,2089年以后未验证
	 * 
	 * @param day1
	 * @param day2
	 * @param escapeYear
	 * @return
	 */
	private static boolean isTheSameDayNoSummerTimeCheck(final Date day1, final Date day2, final boolean escapeYear) {
		final long t1 = day1.getTime(), t2 = day2.getTime();

		if (escapeYear) {
			final long offset4Y1 = (t1 + HOUR8) % YEAR4, offset4Y2 = (t2 + HOUR8) % YEAR4;
			if (offset4Y1 < LEAP_DAY_START_IN_4Y)// d1在闰日前
			{
				if (offset4Y2 < LEAP_DAY_START_IN_4Y) {
					return offset4Y1 % YEAR_COMMON / DAY == offset4Y2 % YEAR_COMMON / DAY;
				} else if (offset4Y2 >= LEAP_DAY_END_IN_4Y) {
					return offset4Y1 % YEAR_COMMON / DAY == (offset4Y2 - DAY) % YEAR_COMMON / DAY;
				} else {
					return false;// d2是闰日
				}
			} else if (offset4Y1 >= LEAP_DAY_END_IN_4Y) {
				if (offset4Y2 < LEAP_DAY_START_IN_4Y) {
					return (offset4Y1 - DAY) % YEAR_COMMON / DAY == offset4Y2 % YEAR_COMMON / DAY;
				} else if (offset4Y2 >= LEAP_DAY_END_IN_4Y) {
					return offset4Y1 % YEAR_COMMON / DAY == offset4Y2 % YEAR_COMMON / DAY;
				} else {
					return false;
				}
			} else {
				// d1是闰日
				return offset4Y2 >= LEAP_DAY_START_IN_4Y && offset4Y2 < LEAP_DAY_END_IN_4Y;
			}
		} else {
			final long t81 = t1 / HOUR8, t82 = t2 / HOUR8;
			if (t81 == t82) {
				return true;
			}
			return (t81 + 1) / 3 == (t82 + 1) / 3;
		}
	}

	/**
	 * 判断给定的时间是不是今天.忽略时分秒
	 * 
	 * @param thatDay
	 * @return
	 */
	public static boolean isToday(final Date thatDay) {
		return isTheSameDay(new Date(), thatDay, false);
	}

//	public static boolean isTheSameDay(final Date day1, final Date day2) { // 将两个日子转成只有日期的字符串
//		return format(YMD, day1).equals(format(YMD, day2));
//	}

	/**
	 * 判断一个年份是否闰年(格里高利历)的简单实现.1582年以后的世纪年必须是400的倍数才是闰年,1582年以前则不论.
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isGregorianLeapYear(final int year) {
		if ((year & 3) != 0) {
			return false;
		}
		// year%4已经==0了
		if (year > 1582) {
			return year % 25 != 0 || year % 16 == 0; // Gregorian
		}
		if (year < 1582) {
			return true; // Julian
		}
		return false;
	}

	public static int dateOf366(final Date d) {
		final Calendar c = Calendar.getInstance();
		c.setTime(d);
		final int rt = c.get(Calendar.DAY_OF_YEAR);
		if (isGregorianLeapYear(c.get(Calendar.YEAR)) || c.get(Calendar.MONTH) < Calendar.MARCH) {
			return rt;
		}
		return rt + 1;
	}

	public static int dateToInt(final Date d) {
		final Calendar c = Calendar.getInstance();
		c.setTime(d);
		return (c.get(Calendar.MONTH) + 1) * 100 + c.get(Calendar.DATE);
	}

	/**
	 * 判断两个日期是否是同一个月
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameMonth(final Date date1, final Date date2) {
		final Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		final Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		if (cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR)) {
			return false;
		}
		return cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
	}

	/**
	 * 返回指定日期到现在的天数（只取正，小于的天数直接返回0）
	 * 
	 * @param date
	 * @return
	 */
	public static long getDaysAfterNow(final Date date) {
		// TODO 使用了文剑封装的C#形式的DateTime，还没测试
		final DateTime dt = new DateTime(date);
		final DateTime now = new DateTime(new Date());
		final TimeSpan span = now.sub(dt);
		final long days = span.getAllDaysCount();
		return days < 0 ? 0 : days;
	}

	/**
	 * 测试某方法的耗时
	 * 
	 * @param times
	 *            运行次数
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	static void timetest(final int times) throws Exception {
		long sum = 0;
		long max = 0;
		for (int i = 0; i < times; i++) {
			final long start = System.currentTimeMillis();
			// 修改下面这行代码,将其改为你要测试的方法
			// Calendar c = Calendar.getInstance();
			// /
			final long end = System.currentTimeMillis();
			final long sub = end - start;
			if (sub > max) {
				max = sub;
			}
			sum += sub;
		}
	}

	/**
	 * (不检测年地)判断compare所表示的日期是否在basic日期的前beforeDays天到后afterDays天这段时间内.
	 * 不是很严格的判断,若数值过大或遇上跨闰年,可能存在隐藏的bug.所以用的时候注意不要搞那么变态的条件进来.
	 * 
	 * @param basic
	 * @param compare
	 * @param beforeDays
	 * @param afterDays
	 * @return
	 */
	public static boolean near(final Calendar basic, final Calendar compare, final int beforeDays, final int afterDays) {
		long sub = basic.getTimeInMillis() - compare.getTimeInMillis();
		final long before = DAY * beforeDays;
		final long after = DAY * afterDays;
		if (sub < 0) {
			if (-sub <= after) {
				return true;
			} else {
				// 有可能是跨年情况,basic是年头,compare是年尾
				compare.add(Calendar.YEAR, -1);
				sub = basic.getTimeInMillis() - compare.getTimeInMillis();
				return sub <= before;
			}
		} else {
			if (sub <= before) {
				return true;
			} else {
				// 有可能是跨年情况,basic是年尾,compare是年头
				compare.add(Calendar.YEAR, 1);
				sub = compare.getTimeInMillis() - basic.getTimeInMillis();
				return sub <= after;
			}
		}
	}

	public static boolean near(final Calendar basic, final int compareMonth, final int compareDay, final int beforeDays, final int afterDays) {
		final Calendar compare = Calendar.getInstance();
		compare.set(Calendar.MONTH, compareMonth - 1);
		compare.set(Calendar.DATE, compareDay);
		compare.set(Calendar.HOUR_OF_DAY, 0);
		compare.set(Calendar.MINUTE, 0);
		compare.set(Calendar.SECOND, 0);
		compare.set(Calendar.MILLISECOND, 0);

		return near(basic, compare, beforeDays, afterDays);
	}

	public static boolean near(final int basicMonth, final int basicDay, final int compareMonth, final int compareDay, final int beforeDays, final int afterDays) {
		final Calendar basic = Calendar.getInstance();
		basic.set(Calendar.MONTH, basicMonth - 1);
		basic.set(Calendar.DATE, basicDay);
		basic.set(Calendar.HOUR_OF_DAY, 0);
		basic.set(Calendar.MINUTE, 0);
		basic.set(Calendar.SECOND, 0);
		basic.set(Calendar.MILLISECOND, 0);

		final Calendar compare = Calendar.getInstance();
		compare.set(Calendar.MONTH, compareMonth - 1);
		compare.set(Calendar.DATE, compareDay);
		compare.set(Calendar.HOUR_OF_DAY, 0);
		compare.set(Calendar.MINUTE, 0);
		compare.set(Calendar.SECOND, 0);
		compare.set(Calendar.MILLISECOND, 0);

		return near(basic, compare, beforeDays, afterDays);
	}

	public static String timeCheck(final Date d) {
		final String time_desc;
		int num;
		final long check = System.currentTimeMillis() - d.getTime();
		if (check < TimeSpan.MINUTE.getTime()) {
			num = (int) (check / TimeSpan.SECOND.getTime());
			if (num == 0) {
				num = 1;
			}
			time_desc = String.valueOf(num) + "秒前";
		} else if (check >= TimeSpan.MINUTE.getTime() && check < TimeSpan.HOUR.getTime()) {
			num = (int) (check / TimeSpan.MINUTE.getTime());
			time_desc = String.valueOf(num) + "分钟前";
		} else if (check >= TimeSpan.HOUR.getTime() && check < TimeSpan.DATE.getTime()) {
			num = (int) (check / TimeSpan.HOUR.getTime());
			time_desc = String.valueOf(num) + "小时前";
		} else {
			num = (int) (check / TimeSpan.DATE.getTime());
			time_desc = String.valueOf(num) + "天前";
		}
		return time_desc;
	}

	public static Date getDateWithoutTime(final Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static long todayStart() {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 将指定的年月日时分秒毫秒转化成毫秒数
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @param millisecond
	 * @return
	 */
	public static long toMillisecond(final int year, final int month, final int date, final int hour, final int minute, final int second, final int millisecond) {
		final long y;
		if (year > YearConst.FIRST_LEAP_YEAR) {
			final int lyCount = leapYearCount(year);
			y = (year - YearConst.START_YEAR) * TimeSpan.YEAR.getTime() + lyCount * TimeSpan.DATE.getTime();
		} else {
			y = (year - YearConst.START_YEAR) * TimeSpan.YEAR.getTime();
		}

		long m = 0;
		final List<TimeSpan> ms = month > 2 && isLeapYear(year) ? TimeSpan.msOfLeapYearMonths() : TimeSpan.msOfCommonYearMonths();
		for (int i = 0; i < month - 1; ++i) {
			m += ms.get(i).getTime();
		}
		return y + m + (date - 1) * TimeSpan.DATE.getTime()//
				+ hour * TimeSpan.HOUR.getTime() + minute * TimeSpan.MINUTE.getTime() + second * TimeSpan.SECOND.getTime() + millisecond;
	}

	/**
	 * 返回从1970年到指定年份间的闰年数
	 * 
	 * @param year
	 * @return
	 */
	public static int leapYearCount(final int year) {
		if (year < YearConst.START_YEAR) {
			throw new IllegalArgumentException("year < " + YearConst.START_YEAR + ':' + year);
		}
		if (year < YearConst.FIRST_LEAP_YEAR) {
			return 0;
		}
		return (year - YearConst.FIRST_LEAP_YEAR) / 4 + 1;
	}

	/**
	 * 返回一个年份是否闰年
	 * 适用于常见年份
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(final int year) {
		if ((year & 3) != 0) {// 非4的倍数
			return false;
		}
		if (year % 25 != 0) {// 非100的倍数
			return true;
		}
		return (year & 15) == 0;// 是100的倍数并且是16的倍数(即400的倍数)
	}

	/**
	 * 获取本时间段所包含的小时数零头,即去掉整天后所剩的小时数(0~23)
	 * 
	 * @return
	 */
	public static int getHours(final long milliseconds) {
		return (int) (milliseconds % DATE / HOUR);
	}

	/**
	 * 获取本时间段所包含的分钟数零头,即去掉整小时后所剩的分钟数(0~59)
	 * 
	 * @return
	 */
	public static int getMinutes(final long milliseconds) {
		return (int) (milliseconds % HOUR / MINUTE);
	}

	/**
	 * 获取本时间段所包含的秒数零头,即去掉整分钟后所剩的秒数(0~59)
	 * 
	 * @return
	 */
	public static int getSeconds(final long milliseconds) {
		return (int) (milliseconds % MINUTE / SECOND);
	}

	/**
	 * 获取本时间段所包含的毫秒数零头,即去掉整天后所剩的毫秒数(0~999)
	 * 
	 * @return
	 */
	public static int getMilliseconds(final long milliseconds) {
		return (int) (milliseconds % SECOND);
	}

	/**
	 * 获取本时间段一共包含多少天
	 * 
	 * @return
	 */
	public static long getAllDaysCount(final long milliseconds) {
		return milliseconds / DATE;
	}

	/**
	 * 获取本时间段一共包含多少小时
	 * 
	 * @return
	 */
	public static long getAllHoursCount(final long milliseconds) {
		return milliseconds / HOUR;
	}

	/**
	 * 获取本时间段一共包含多少分钟
	 * 
	 * @return
	 */
	public static long getAllMinutesCount(final long milliseconds) {
		return milliseconds / MINUTE;
	}

	/**
	 * 获取本时间段一共包含多少秒钟
	 * 
	 * @return
	 */
	public static long getAllSecondsCount(final long milliseconds) {
		return milliseconds / SECOND;
	}

	public static boolean isLeapYear() {
		return isLeapYear(nowYear());
	}

	public static final List<Integer> getCurYearDaysByMonth() {
		return isLeapYear() ? LEAP_YEAR_DAYS_BY_MONTH : COMMON_YEAR_DAYS_BY_MONTH;
	}

	public static int nowYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	public static List<Date> thisWeek() {
		final Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, 1);
		int size = 7;
		final List<Date> list = new ArrayList<>(size);
		while (true) {
			list.add(c.getTime());
			if (--size == 0) {
				break;
			}
			c.add(Calendar.DATE, 1);
		}
		return list;
	}

	public static List<Date> rangeOf(final Date date, final int beforeDates, final int afterDates) {
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		final int size = beforeDates + afterDates + 1;
		final List<Date> list = Arrays.asList(new Date[size]);
		list.set(beforeDates, date);
		for (int i = beforeDates; i > 0;) {
			c.add(Calendar.DATE, -1);
			list.set(--i, c.getTime());
		}
		c.add(Calendar.DATE, size);
		final int j = beforeDates + 1;
		for (int i = size; i > j;) {
			c.add(Calendar.DATE, -1);
			list.set(--i, c.getTime());
		}
		return list;
	}

	public static DateTime yesterday() {
		return new DateTime().sub(new TimeSpan(TimeUnit.DAYS.toMillis(1)));
	}

	public static void main(final String... args) {
		final String[] sources = {//
		"2010-01-02 00:00:00.000",//
				" 2010-  03-01 00:00:00",//
				"	2010-1-1 00:00:00",//
				"2010-01			-01 0:0:0",//
				"2010/1/1 ",//
				"2010.1.1",//
				"20101223",//
				"12252010",//
		};
		final SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (final String s : sources) {
			System.out.println("@@@@@@" + f.format(parse(s)));
		}

		System.out.println("@@@@@@" + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date()));

		// System.out.println("@@@@@@" + rangeOf(new Date(), 3, 4));

		// final long[] ls = { SECOND, MINUTE, HOUR,
		// /**
		// * 8小时的毫秒数.用于时区计算.中国是+8时区.
		// */
		// HOUR8,
		// /**
		// * 16小时的毫秒数.用于时区计算.中国是+8时区,对24的补数是16.
		// */
		// HOUR16, DATE, DAY, WEEK, DAY7, DAY28, DAY29, DAY30, DAY31, DAY365,
		// DAY366, YEAR_COMMON, YEAR2, YEAR_LEAP, };
		// System.out.println(Arrays.toString(ls));
	}
}
