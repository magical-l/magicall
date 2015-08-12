package me.magicall.mark;

public interface HasId<N extends Comparable<N>> extends HasIdGetter<N> {

	void setId(N id);

	interface HasIntId extends HasId<Integer>, HasIntIdGetter {
		@Override
		void setId(Integer id);
	}

	interface HasLongId extends HasId<Long>, HasLongIdGetter {
		@Override
		void setId(Long id);
	}

	interface HasStrId extends HasId<String>, HasStrIdGetter {
		@Override
		void setId(String id);
	}
}
