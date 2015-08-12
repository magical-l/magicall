package me.magicall.coll.unmodifiable;

import me.magicall.mark.Unmodifiable;
import me.magicall.mark.Wrapper;

import java.io.Serializable;
import java.util.Map.Entry;

public abstract class UnmodifiableEntryTemplate<K, V>
        implements Entry<K, V>, Unmodifiable, Serializable, Wrapper<Entry<K, V>> {

    private static final long serialVersionUID = 4560945985789158736L;

    @Override
    public final V setValue(final V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Entry<K, V> unwrap() {
        return this;
    }
}
