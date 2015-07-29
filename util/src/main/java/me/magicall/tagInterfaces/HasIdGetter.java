package me.magicall.tagInterfaces;

public interface HasIdGetter<I> {

	I getId();

	public static interface HasIntIdGetter extends HasIdGetter<Integer> {
		@Override
		Integer getId();
	}

	public static interface HasLongIdGetter extends HasIdGetter<Long> {
		@Override
		Long getId();
	}

	public static interface HasStrIdGetter extends HasIdGetter<String> {
		@Override
		String getId();
	}
}
