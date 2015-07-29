/**
 * 
 */
package me.magicall.util.time;

import java.util.Date;

import me.magicall.consts.TimeConst.DateConst;
import me.magicall.util.kit.WithSubclassKit;

public final class DateKit extends WithSubclassKit<Date> {

	private static final Class<Date> CLASS_ARR = Date.class;
	//--------------------------------------------
	public static final DateKit INSTANCE = new DateKit();

	//---------------------------------------------
	private DateKit() {
		super(CLASS_ARR, DateConst._0);
	}

	//---------------------------------------------
	@Override
	public Date fromString(final String source) {
		return TimeUtil.parse(source);
	}

	//=============================================

	private static final long serialVersionUID = 3971538824614754296L;

	private Object readResolve() {
		return INSTANCE;
	}
}