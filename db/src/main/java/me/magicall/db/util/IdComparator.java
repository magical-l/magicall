package me.magicall.db.util;

import java.util.Comparator;

import me.magicall.db.model.BaseModel;
import me.magicall.util.comparator.ComparatorAndEquivalentUsingComparbleFieldTemplate.SerializableComparatorAndEquivalentUsingFieldTemplate;

public class IdComparator<I extends Number & Comparable<I>> extends SerializableComparatorAndEquivalentUsingFieldTemplate<BaseModel<I>>//
		implements Comparator<BaseModel<I>> {

	@SuppressWarnings("rawtypes")
	private static final IdComparator INSTANCE = new IdComparator();

	@SuppressWarnings("unchecked")
	public static <N extends Number & Comparable<N>> Comparator<BaseModel<N>> getInstance() {
		return INSTANCE;
	}

	private static final long serialVersionUID = -8162109412077962798L;

	@Override
	protected Comparable<?> comparableField(final BaseModel<I> o) {
		return o.getId();
	}
}
