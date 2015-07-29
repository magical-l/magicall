/**
 * Magical Liang
 * 2009-4-25 上午10:58:00
 */
package me.magicall.util.time;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * 时间段类。本类使用最简单的方式表示一段时间，是一个向量，有正负。
 * </pre>
 * 
 * @author Magical Liang
 */
public final class TimeSpan implements Serializable, Comparable<TimeSpan> {
	public static final TimeSpan ZERO = new TimeSpan(0);
	public static final TimeSpan MILLESECOND = new TimeSpan(TimeUnit.MILLISECONDS.toMillis(1));
	public static final TimeSpan SECOND = new TimeSpan(TimeUnit.SECONDS.toMillis(1));
	public static final TimeSpan MINUTE = new TimeSpan(TimeUnit.MINUTES.toMillis(1));
	public static final TimeSpan HOUR = new TimeSpan(TimeUnit.HOURS.toMillis(1));
	public static final TimeSpan HOUR8 = new TimeSpan(TimeUnit.HOURS.toMillis(8));
	public static final TimeSpan HOUR16 = new TimeSpan(TimeUnit.HOURS.toMillis(16));
	public static final TimeSpan DATE = new TimeSpan(TimeUnit.DAYS.toMillis(1));
	public static final TimeSpan WEEK = new TimeSpan(TimeUnit.DAYS.toMillis(7));
	public static final TimeSpan DATE28 = new TimeSpan(TimeUnit.DAYS.toMillis(28));
	public static final TimeSpan DATE29 = new TimeSpan(TimeUnit.DAYS.toMillis(29));
	public static final TimeSpan DATE30 = new TimeSpan(TimeUnit.DAYS.toMillis(30));
	public static final TimeSpan DATE31 = new TimeSpan(TimeUnit.DAYS.toMillis(31));
	public static final TimeSpan DATE90 = new TimeSpan(TimeUnit.DAYS.toMillis(90));
	public static final TimeSpan DATE180 = new TimeSpan(TimeUnit.DAYS.toMillis(180));
	public static final TimeSpan DATE365 = new TimeSpan(TimeUnit.DAYS.toMillis(365));
	public static final TimeSpan DATE366 = new TimeSpan(TimeUnit.DAYS.toMillis(366));
	public static final TimeSpan YEAR = DATE365;
	public static final TimeSpan YEAR2 = new TimeSpan(YEAR.time * 2);
	public static final TimeSpan YEAR_LEAP = DATE366;
	public static final TimeSpan YEAR4 = new TimeSpan(YEAR.time * 3 + YEAR_LEAP.time);

	/*
	 * 平年月份的天数
	 */
	private static final TimeSpan[] MONTHS_COMMON = {//
	/*    */DATE31, DATE28, DATE31,//
			DATE30, DATE31, DATE30,//
			DATE31, DATE31, DATE30,//
			DATE31, DATE30, DATE31, };

	private static final List<TimeSpan> MONTHS_COMMON_COLLECTION = Collections.unmodifiableList(Arrays.asList(MONTHS_COMMON));

	/**
	 * 平年月份的天数
	 */
	public static List<TimeSpan> msOfCommonYearMonths() {
		return MONTHS_COMMON_COLLECTION;
	}

	/*
	 * 闰年月份天数
	 */
	private static final TimeSpan[] MONTHS_LEAP = {//
	/*    */DATE31, DATE29, DATE31, DATE30,//
			DATE31, DATE30, DATE31, DATE31,//
			DATE30, DATE31, DATE30, DATE31, };

	private static final List<TimeSpan> MONTHS_LEAP_COLLECTION = Collections.unmodifiableList(Arrays.asList(MONTHS_LEAP));

	/**
	 * 闰年月份天数
	 */
	public static List<TimeSpan> msOfLeapYearMonths() {
		return MONTHS_LEAP_COLLECTION;
	}

	// ======================================================
	private final long time;

	private transient volatile String stringValue;

	// ======================================================
	public TimeSpan(final long time) {
		this.time = time;
	}

	// ======================================================
	/**
	 * 获取本时间段一共包含多少天
	 * 
	 * @return
	 */
	public long getAllDaysCount() {
		return TimeUtil.getAllDaysCount(time);
	}

	/**
	 * 获取本时间段一共包含多少小时
	 * 
	 * @return
	 */
	public long getAllHoursCount() {
		return TimeUtil.getAllHoursCount(time);
	}

	/**
	 * 获取本时间段一共包含多少分钟
	 * 
	 * @return
	 */
	public long getAllMinutesCount() {
		return TimeUtil.getAllMinutesCount(time);
	}

	/**
	 * 获取本时间段一共包含多少秒钟
	 * 
	 * @return
	 */
	public long getAllSecondsCount() {
		return TimeUtil.getAllSecondsCount(time);
	}

	/**
	 * 获取本时间段一共包含多少毫秒
	 * 
	 * @return
	 */
	public long getAllMillisecondsCount() {
		return time;
	}

	/**
	 * 获取本时间段所包含的小时数零头,即去掉整天后所剩的小时数(0~23)
	 * 
	 * @return
	 */
	public int getHours() {
		return TimeUtil.getHours(time);
	}

	/**
	 * 获取本时间段所包含的分钟数零头,即去掉整小时候所剩的分钟数(0~59)
	 * 
	 * @return
	 */
	public int getMinutes() {
		return TimeUtil.getMinutes(time);
	}

	/**
	 * 获取本时间段所包含的秒数零头,即去掉整分钟后所剩的秒数(0~59)
	 * 
	 * @return
	 */
	public int getSeconds() {
		return TimeUtil.getSeconds(time);
	}

	/**
	 * 获取本时间段所包含的毫秒数零头,即去掉整天后所剩的毫秒数(0~999)
	 * 
	 * @return
	 */
	public int getMilliseconds() {
		return TimeUtil.getMilliseconds(time);
	}

	/**
	 * 返回本时间段的毫秒数
	 * 
	 * @return
	 */
	public long getTime() {
		return time;
	}

	// ==================================================================
	/**
	 * 时间段相加,获得一个更长的时间段
	 * 
	 * @param other
	 * @return
	 */
	public TimeSpan add(final TimeSpan other) {
		return new TimeSpan(time + other.time);
	}

	/**
	 * 时间段相加,获得一个更长的时间段
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public TimeSpan add(final TimeSpan o1, final TimeSpan o2) {
		return new TimeSpan(time + o1.time + o2.time);
	}

	/**
	 * 时间段相加,获得一个更长的时间段
	 * 
	 * @param o1
	 * @param o2
	 * @param o3
	 * @return
	 */
	public TimeSpan add(final TimeSpan o1, final TimeSpan o2, final TimeSpan o3) {
		return new TimeSpan(time + o1.time + o2.time + o3.time);
	}

	/**
	 * 时间段相加,获得一个更长的时间段
	 * 
	 * @param other
	 * @param others
	 * @return
	 */
	public TimeSpan add(final TimeSpan other, final TimeSpan... others) {
		return new TimeSpan(add(time + other.time, others));
	}

	/*
	 * 注意:此处并没有检查溢出
	 */
	static long add(long t, final TimeSpan... others) {
		for (final TimeSpan s : others) {
			t += s.time;
		}
		return t;
	}

	/**
	 * 将本时间段加到某时间点上,获得该时间点经过本时间段之后的时间点
	 * 
	 * @param timePoint
	 * @return
	 */
	public DateTime add(final DateTime timePoint) {
		return new DateTime(time + timePoint.getTime());
	}

	/**
	 * 时间段相减,得到一个比较短的时间段
	 * 
	 * @param other
	 * @return
	 */
	public TimeSpan sub(final TimeSpan other) {
		return new TimeSpan(time - other.time);
	}

	/**
	 * 时间段相减,得到一个比较短的时间段
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public TimeSpan sub(final TimeSpan o1, final TimeSpan o2) {
		return new TimeSpan(time - o1.time - o2.time);
	}

	/**
	 * 时间段相减,得到一个比较短的时间段
	 * 
	 * @param o1
	 * @param o2
	 * @param o3
	 * @return
	 */
	public TimeSpan sub(final TimeSpan o1, final TimeSpan o2, final TimeSpan o3) {
		return new TimeSpan(time - o1.time - o2.time - o3.time);
	}

	/**
	 * 时间段相减,得到一个比较短的时间段
	 * 
	 * @param others
	 * @return
	 */
	public TimeSpan sub(final TimeSpan... others) {
		return new TimeSpan(sub(time, others));
	}

	/**
	 * 本时间段占另一时间段的比例
	 * 
	 * @param other
	 * @return
	 */
	public double div(final TimeSpan other) {
		return time / other.time;
	}

	/**
	 * 本时间段的若干倍
	 * 
	 * @param times 倍数
	 * @return
	 */
	public TimeSpan multi(final int times) {
		return new TimeSpan(time * times);
	}

	/*
	 * 注意:此处并没有检查溢出
	 */
	static long sub(long t, final TimeSpan... others) {
		for (final TimeSpan s : others) {
			t -= s.time;
		}
		return t;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + (int) (time ^ time >>> 32);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TimeSpan other = (TimeSpan) obj;
		if (time != other.time) {
			return false;
		}
		return true;
	}

	public Date toDate() {
		return new DateTime(time);
	}

	public String format(final DateFormat dateFormat) {
		return dateFormat.format(toDate());
	}

	public String format(final String pattern) {
		return format(new SimpleDateFormat(pattern));
	}

	@Override
	public String toString() {
		if (stringValue == null) {
			stringValue = new StringBuilder().append(getAllDaysCount()).append(" days ")//
					.append(getHours()).append(" hours ")//
					.append(getMinutes()).append(" minutes ")//
					.append(getSeconds()).append(" seconds ")//
					.append(getMilliseconds()).append(" ms ").toString();
		}
		return stringValue;
	}

	@Override
	public int compareTo(final TimeSpan o) {
		if (time > o.time) {
			return 1;
		} else if (time < o.time) {
			return -1;
		}
		return 0;
	}

	public static void main(final String... args) {
		final TimeSpan span = new TimeSpan(1);
		System.out.println("@@@@@@" + span.add(new TimeSpan(2), new TimeSpan(3)).time);
	}

	private static final long serialVersionUID = 5777743030392159993L;
}
