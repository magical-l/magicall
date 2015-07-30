package me.magicall.coll.unmodifiable;

import java.io.Serializable;
import java.util.Map.Entry;

import me.magicall.mark.Unmodifiable;
import me.magicall.mark.Wrapper;

public abstract class UnmodifiableEntryTemplate<K, V> implements Entry<K, V>, Unmodifiable, Serializable, Wrapper {

	private static final long serialVersionUID = 4560945985789158736L;

	@Override
	public final V setValue(final V value) {
		throw new UnsupportedOperationException();
	}
}
