/**
 * 
 */
package me.magicall.util.kit;

public abstract class WithSubclassKit<T> extends Kit<T> {

	private static final long serialVersionUID = 6893831897487831367L;

	//---------------------------------------------
	public WithSubclassKit(final Class<T> mainClass, final T emptyValue, final String... shortNames) {
		super(mainClass, emptyValue, shortNames);
	}

	public WithSubclassKit(final Class<T> mainClass, final T emptyValue) {
		super(mainClass, emptyValue);
	}

	//---------------------------------------------
	@Override
	public boolean isThesaurusesClass(final Class<?> clazz) {
		final Class<?>[] cs = getClasses();
		for (final Class<?> c : cs) {
			if (c.isAssignableFrom(clazz)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public T fromString(final String source) {
		// 这玩意怎么搞?你告诉我
		return null;
	}
}