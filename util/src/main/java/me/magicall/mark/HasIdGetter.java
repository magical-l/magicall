package me.magicall.mark;

@FunctionalInterface
public interface HasIdGetter<I> {

	I getId();

	@FunctionalInterface
	interface HasIntIdGetter extends HasIdGetter<Integer> {
		@Override
		Integer getId();
	}

	@FunctionalInterface
	interface HasLongIdGetter extends HasIdGetter<Long> {
		@Override
		Long getId();
	}

	@FunctionalInterface
	interface HasStrIdGetter extends HasIdGetter<String> {
		@Override
		String getId();
	}
}
