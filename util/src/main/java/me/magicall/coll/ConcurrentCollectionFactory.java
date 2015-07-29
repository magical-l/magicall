package me.magicall.coll;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentCollectionFactory implements CollectionFactory {

	@Override
	public <E> ArrayList<E> newArrayList() {
		return new ArrayList<>();
	}

	@Override
	public <E> Collection<E> newColl() {
		return newArrayList();
	}

	@Override
	public <E> LinkedList<E> newLinkedList() {
		return new LinkedList<>();
	}

	@Override
	public <K, V> Map<K, V> newMap() {
		return new ConcurrentHashMap<>();
	}

	@Override
	public <E> Set<E> newSet() {
		return Collections.synchronizedSet(new HashSet<>());
	}

}
