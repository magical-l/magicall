/**
 * 
 */
package me.magicall.coll.util;

import java.io.Serializable;

import me.magicall.coll.KeyAccessor;
import me.magicall.mark.HasIdGetter;

/**
 * @author Administrator
 * @version Mar 19, 2011 9:06:11 AM
 */
public enum IdAccessor implements KeyAccessor<Object, HasIdGetter<?>>, Serializable {
	INSTANCE;

	@Override
	public Object extract(final HasIdGetter<?> value) {
		return value.getId();
	}
}
