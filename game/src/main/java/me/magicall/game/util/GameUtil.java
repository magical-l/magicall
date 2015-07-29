package me.magicall.game.util;

import java.util.Comparator;

import me.magicall.game.io.GameOutput;
import me.magicall.game.io.OutputSource;
import me.magicall.game.map.Coordinate;
import me.magicall.tagInterfaces.HasOrder;
import me.magicall.util.comparator.SerializableComparator;

public class GameUtil {

	public static final Comparator<Coordinate> COORDINATE_COMPARATOR = new SerializableComparator<Coordinate>() {
		private static final long serialVersionUID = -978700936405457908L;

		@Override
		public int compare(final Coordinate o1, final Coordinate o2) {
			final int[] is1 = o1.getCoordinateNums();
			final int[] is2 = o2.getCoordinateNums();
			for (int i = 0; i < is1.length; ++i) {
				if (is1[i] > is2[i]) {
					return 1;
				} else if (is1[i] < is2[i]) {
					return -1;
				}
			}
			return 0;
		}
	};

	public static final Comparator<HasOrder> HAS_ORDER_COMPARATOR = new SerializableComparator<HasOrder>() {

		private static final long serialVersionUID = 6873102270567256368L;

		@Override
		public int compare(final HasOrder o1, final HasOrder o2) {
			return o1.getOrder() - o2.getOrder();
		}
	};

	@SuppressWarnings("unchecked")
	public static <H extends HasOrder> Comparator<H> getComparator() {
		return (Comparator<H>) HAS_ORDER_COMPARATOR;
	}

	public static void showException(final OutputSource outputSource, final GameOutput gameOutput, final Throwable e) {
		Throwable throwable = e;
		for (Throwable t = throwable.getCause(); t != null; t = throwable.getCause()) {
			throwable = t;
		}
		gameOutput.output(outputSource, throwable.getLocalizedMessage());
	}
}
