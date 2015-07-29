package me.magicall.util.time;

import java.util.Date;

/**
 * 两个时间点表示的一段客观时间段
 * 
 * @author MaGiCalL
 */
public class TimePin {

	private Date fromTime;

	private Date toTime;

	public TimePin() {
		super();
	}

	public TimePin(final Date fromTime, final Date toTime) {
		super();
		this.fromTime = fromTime;
		this.toTime = toTime;
	}

	public Date getFromTime() {
		return fromTime;
	}

	public void setFromTime(final Date fromTime) {
		this.fromTime = fromTime;
	}

	public Date getToTime() {
		return toTime;
	}

	public void setToTime(final Date toTime) {
		this.toTime = toTime;
	}

}
