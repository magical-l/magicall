/**
 * Magical Liang
 * 2009-4-25 上午11:14:18
 */
package me.magicall.util.time;

import java.util.Calendar;
import java.util.Date;

import me.magicall.consts.TimeConst.YearConst;

/**
 * <pre>
 * 本类表示一个时间点,实现简单的,以中国时区(+8时区)为准的公历纪元.
 * 本类对象不可变
 * </pre>
 * 
 * @author Magical Liang
 */
public final class DateTime extends Date {
	public static final DateTime START = new DateTime(YearConst.START_YEAR, 1, 1);

	public static DateTime fromDate(final Date date) {
		return date instanceof DateTime ? (DateTime) date : new DateTime(date);
	}

	// =========================================================
	private transient Integer myYear;
	private transient Integer myMonth;
	private transient Integer myDate;
	private transient Integer myDay;

	private transient String stringValue;

	// =========================================================
	public DateTime(final long time) {
		super(time);
	}

	public DateTime() {
		this(System.currentTimeMillis());
	}

	public DateTime(final Date time) {
		this(time.getTime());
	}

	public DateTime(final int year, final int month, final int day, final int hour, final int minute, final int second, final int millisecond) {
		this(TimeUtil.toMillisecond(year, month, day, hour, minute, second, millisecond));
		myYear = year;
		myMonth = month;
	}

	public DateTime(final int year, final int month, final int day) {
		this(TimeUtil.toMillisecond(year, month, day, 0, 0, 0, 0));
		myYear = year;
		myMonth = month;
	}

	// ===========================================================
	/**
	 * 返回本时间点所在年份是否闰年
	 * 
	 * @return
	 */
	public boolean isLeapYear() {
		return TimeUtil.isLeapYear(getYear());
	}

	public boolean less(final DateTime other) {
		return getTime() < other.getTime();
	}

	public boolean lessEqual(final DateTime other) {
		return getTime() <= other.getTime();
	}

	public boolean greate(final DateTime other) {
		return getTime() > other.getTime();
	}

	public boolean greateEqual(final DateTime other) {
		return getTime() >= other.getTime();
	}

	/**
	 * 获取本时间点过一段时间之后的时间点
	 * 
	 * @param distance 过的那一段时间
	 * @return
	 */
	public DateTime add(final TimeSpan distance) {
		return new DateTime(getTime() + distance.getTime());
	}

	public DateTime add(final TimeSpan distance1, final TimeSpan distance2) {
		return new DateTime(getTime() + distance1.getTime() + distance2.getTime());
	}

	public DateTime add(final TimeSpan distance1, final TimeSpan distance2, final TimeSpan distance3) {
		return new DateTime(getTime() + distance1.getTime() + distance2.getTime() + distance3.getTime());
	}

	public DateTime add(final TimeSpan d1, final TimeSpan... other) {
		return new DateTime(TimeSpan.add(getTime(), other));
	}

	/**
	 * 获取本时间点之前一段时间的时间点
	 * 
	 * @param distance 之前的一段时间
	 * @return
	 */
	public DateTime sub(final TimeSpan distance) {
		return new DateTime(getTime() - distance.getTime());
	}

	public DateTime sub(final TimeSpan distance1, final TimeSpan distance2) {
		return new DateTime(getTime() - distance1.getTime() - distance2.getTime());
	}

	public DateTime sub(final TimeSpan distance1, final TimeSpan distance2, final TimeSpan distance3) {
		return new DateTime(getTime() - distance1.getTime() - distance2.getTime() - distance3.getTime());
	}

	public DateTime sub(final TimeSpan d1, final TimeSpan... distances) {
		return new DateTime(TimeSpan.sub(getTime(), distances));
	}

	/**
	 * 两个时间点的时间差
	 * 
	 * @param otherPoint
	 * @return
	 */
	public TimeSpan sub(final DateTime otherPoint) {
		return new TimeSpan(getTime() - otherPoint.getTime());
	}

	/**
	 * 判断两个时间点是否同一天(忽略小于日的时间单位)
	 * 
	 * @param otherDay
	 * @param escapeYear 指定是否不比较年份
	 * @return
	 */
	public boolean isTheSameDayWith(final Date otherDay, final boolean escapeYear) {
		final DateTime other = otherDay instanceof DateTime ? (DateTime) otherDay : new DateTime(otherDay);
		if (escapeYear) {
			return getMonth() == other.getMonth() && getDate() == other.getDate();
		} else {
			return getAllDaysCount() == other.getAllDaysCount();
		}
	}

	/**
	 * 判断两个时间点是否同一天(忽略小于日的时间单位)
	 * 
	 * @param otherDay
	 * @return
	 */
	public boolean isTheSameDayWith(final Date otherDay) {
		return isTheSameDayWith(otherDay, false);
	}

	private void reset() {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(this);
		myYear = calendar.get(Calendar.YEAR);
		myMonth = calendar.get(Calendar.MONTH) + 1;
		myDate = calendar.get(Calendar.DATE);
		myDay = calendar.get(Calendar.DAY_OF_WEEK) + 1;
	}

	@Override
	public int getYear() {
		if (myYear == null) {
			reset();
		}
		return myYear;
	}

	@Override
	public int getMonth() {
		if (myMonth == null) {
			reset();
		}
		return myMonth;
	}

	@Override
	public int getDate() {
		if (myDate == null) {
			reset();
		}
		return myDate;
	}

	@Override
	public int getDay() {
		if (myDay == null) {
			reset();
		}
		return myDay;
	}

	/**
	 * 以格式"yyyy-MM-dd HH:mm:ss.SSS"返回本时间点
	 */
	@Override
	public String toString() {
		if (stringValue == null) {
			stringValue = getYear() + "-" + getMonth() + '-' + getDate() + ' ' + getHours() + ':' + getMinutes() + ':' + getSeconds() + '.'
					+ getMilliseconds();
		}
		return stringValue;
	}

	@Override
	public Object clone() {
		return new DateTime(getTime());
	}

	/**
	 * 获取自1970年1月1日0时0分0秒起到本时间共经过的天数
	 * 
	 * @return
	 * @see com.xiaonei.vip.commons.util.time.TimeSpan#getAllDaysCount()
	 */
	public long getAllDaysCount() {
		return TimeUtil.getAllDaysCount(getTime());
	}

	/**
	 * 获取自1970年1月1日0时0分0秒起到本时间共经过的小时数
	 * 
	 * @return
	 * @see com.xiaonei.vip.commons.util.time.TimeSpan#getAllHoursCount()
	 */
	public long getAllHoursCount() {
		return TimeUtil.getAllHoursCount(getTime());
	}

	/**
	 * 获取自1970年1月1日0时0分0秒起到本时间共经过的毫秒数
	 * 
	 * @return
	 * @see com.xiaonei.vip.commons.util.time.TimeSpan#getAllMillisecondsCount()
	 */
	public long getAllMillisecondsCount() {
		return getTime();
	}

	/**
	 * 获取自1970年1月1日0时0分0秒起到本时间共经过的分钟数
	 * 
	 * @return
	 * @see com.xiaonei.vip.commons.util.time.TimeSpan#getAllMinutesCount()
	 */
	public long getAllMinutesCount() {
		return TimeUtil.getAllMinutesCount(getTime());
	}

	/**
	 * 获取自1970年1月1日0时0分0秒起到本时间共经过的秒数
	 * 
	 * @return
	 * @see com.xiaonei.vip.commons.util.time.TimeSpan#getAllSecondsCount()
	 */
	public long getAllSecondsCount() {
		return TimeUtil.getAllSecondsCount(getTime());
	}

	/**
	 * 获取本时间点在本天的几点
	 * 
	 * @return
	 * @see com.xiaonei.vip.commons.util.time.TimeSpan#getHours()
	 */
	@Override
	public int getHours() {
		return TimeUtil.getHours(getTime());
	}

	/**
	 * 获取本时间点在本天的几点
	 * 
	 * @return
	 * @see com.xiaonei.vip.commons.util.time.TimeSpan#getMilliseconds()
	 */
	public int getMilliseconds() {
		return TimeUtil.getMilliseconds(getTime());
	}

	/**
	 * 获取本时间点在本小时的几分
	 * 
	 * @return
	 * @see com.xiaonei.vip.commons.util.time.TimeSpan#getMinutes()
	 */
	@Override
	public int getMinutes() {
		return TimeUtil.getMinutes(getTime());
	}

	/**
	 * 获取本时间点在本分的几秒
	 * 
	 * @return
	 * @see com.xiaonei.vip.commons.util.time.TimeSpan#getSeconds()
	 */
	@Override
	public int getSeconds() {
		return TimeUtil.getSeconds(getTime());
	}

	/**
	 * @return
	 * @see com.xiaonei.vip.commons.util.time.TimeSpan#getTime()
	 */
	@Override
	public long getTime() {
		return super.getTime();
	}

	/**
	 * 不支持的操作
	 */
	@Override
	public void setDate(final int _date) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 不支持的操作
	 */
	@Override
	public void setHours(final int _hours) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 不支持的操作
	 */
	@Override
	public void setMinutes(final int _minutes) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 不支持的操作
	 */
	@Override
	public void setMonth(final int _month) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 不支持的操作
	 */
	@Override
	public void setSeconds(final int _seconds) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 不支持的操作
	 */
	@Override
	public void setTime(final long _time) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 不支持的操作
	 */
	@Override
	public void setYear(final int _year) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 为了兼容一部分使用Date类型的应用（比如用getClass方式判断之类的框架）
	 * 
	 * @return
	 */
	public Date toDate() {
		return new Date(super.getTime());
	}

	public DateTime ceilDate() {
		return new DateTime((getAllDaysCount() + 1) * TimeSpan.DATE.getTime());
	}

	public DateTime ceilHour() {
		return new DateTime((getAllHoursCount() + 1) * TimeSpan.HOUR.getTime());
	}

	public DateTime ceilMinute() {
		return new DateTime((getAllMinutesCount() + 1) * TimeSpan.MINUTE.getTime());
	}

	public DateTime ceilSecond() {
		return new DateTime((getAllSecondsCount() + 1) * TimeSpan.SECOND.getTime());
	}

	public DateTime floorDate() {
		return new DateTime(getAllDaysCount() * TimeSpan.DATE.getTime());
	}

	public DateTime floorHour() {
		return new DateTime(getAllHoursCount() * TimeSpan.HOUR.getTime());
	}

	public DateTime floorMinute() {
		return new DateTime(getAllMinutesCount() * TimeSpan.MINUTE.getTime());
	}

	public DateTime floorSecond() {
		return new DateTime(getAllSecondsCount() * TimeSpan.SECOND.getTime());
	}

	//==========================================
	public static DateTime todayStart() {
		return new DateTime(TimeUtil.todayStart());
	}

	public static DateTime thisDayStart() {
		return todayStart();
	}

	public static DateTime thisHourStart() {
		return new DateTime(new DateTime().getAllHoursCount());
	}

	public static DateTime thisMinuteStart() {
		return new DateTime(new DateTime().getAllMinutesCount());
	}

	public static void main(final String... args) {
		final Date d = new Date(0);
		final DateTime dt = new DateTime(0);
		System.out.println(d);
		System.out.println(dt);
		System.out.println(d.getTime());
		System.out.println(dt.getTime());
	}

	private static final long serialVersionUID = 8674981431221127468L;

}
