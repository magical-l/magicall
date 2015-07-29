package me.magicall.util.time;

import java.io.Serializable;
import java.util.Date;

public class TimePart implements Serializable {

	private static final long serialVersionUID = -6167449102575265053L;

	private final Date from;
	private final Date to;

	public TimePart(final Date from, final Date to) {
		super();
		this.from = from;
		this.to = to;
	}

	public Date getFrom() {
		return from;
	}

	public Date getTo() {
		return to;
	}

	@Override
	public String toString() {
		return TimeFormatter.Y4_M2_D2_H2_MIN2_S2_MS3.format(from) + " ~ " + TimeFormatter.Y4_M2_D2_H2_MIN2_S2_MS3.format(to);
	}
}
