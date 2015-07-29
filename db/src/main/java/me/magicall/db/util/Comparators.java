package me.magicall.db.util;

import me.magicall.db.model.modelInterfaces.HasAddTime;
import me.magicall.db.model.modelInterfaces.HasUptime;
import me.magicall.util.comparator.ComparatorUtil;
import me.magicall.util.comparator.ComparatorWithEquivalent;
import me.magicall.util.comparator.ComparatorAndEquivalentUsingComparbleFieldTemplate.SerializableComparatorAndEquivalentUsingFieldTemplate;

public class Comparators extends ComparatorUtil {

	protected Comparators() {

	}

	public static final ComparatorWithEquivalent<HasAddTime> ADD_TIME_COMPARATOR = new SerializableComparatorAndEquivalentUsingFieldTemplate<HasAddTime>() {
		private static final long serialVersionUID = 7314825479631799094L;

		@Override
		protected Comparable<?> comparableField(final HasAddTime o) {
			return o.getAddTime();
		}
	};

	public static final ComparatorWithEquivalent<HasUptime> UPTIME_COMPARATOR = new SerializableComparatorAndEquivalentUsingFieldTemplate<HasUptime>() {
		private static final long serialVersionUID = 5860014534776698585L;

		@Override
		protected Comparable<?> comparableField(final HasUptime o) {
			return o.getUptime();
		}
	};
}
