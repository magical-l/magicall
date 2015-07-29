package me.magicall.coll;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public interface CollectionFactory {

	<E> Collection<E> newColl();

	<E> ArrayList<E> newArrayList();

	<E> LinkedList<E> newLinkedList();

	<E> Set<E> newSet();

	<K, V> Map<K, V> newMap();

}
