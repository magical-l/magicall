package me.magicall.coll;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class CommonCollectionFactory implements CollectionFactory {

	public static final CommonCollectionFactory INSTANCE = new CommonCollectionFactory();

	private CommonCollectionFactory() {
	}

	@Override
	public <E> Collection<E> newColl() {
		return newArrayList();
	}

	@Override
	public <E> ArrayList<E> newArrayList() {
		return new ArrayList<>();
	}

	@Override
	public <K, V> Map<K, V> newMap() {
		return new HashMap<>();
	}

	@Override
	public <E> Set<E> newSet() {
		return new HashSet<>();
	}

	@Override
	public <E> LinkedList<E> newLinkedList() {
		return new LinkedList<>();
	}

}
