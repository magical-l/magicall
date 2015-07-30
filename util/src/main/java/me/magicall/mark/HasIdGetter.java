package me.magicall.mark;

public interface HasIdGetter<I> {

	I getId();

	interface HasIntIdGetter extends HasIdGetter<Integer> {
		@Override
		Integer getId();
	}

	interface HasLongIdGetter extends HasIdGetter<Long> {
		@Override
		Long getId();
	}

	interface HasStrIdGetter extends HasIdGetter<String> {
		@Override
		String getId();
	}
}
