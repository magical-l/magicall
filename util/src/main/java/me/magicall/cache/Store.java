package me.magicall.cache;

import me.magicall.mark.ReloadableCache;
import me.magicall.util.kit.Kits;

import java.util.Date;

public abstract class Store<T> implements ReloadableCache {

	protected T data;
	protected Date buildTime;

	protected T getData() {
		return data;
	}

	protected void buildData() {
		final Date now = new Date();
		final T t = Kits.OBJ.checkToDefaultValue(buildDataInternal(), emptyData());
		data = t;
		buildTime = now;
	}

	protected abstract T buildDataInternal();

	public final Date getBuildTime() {
		return buildTime;
	}

	protected T emptyData() {
		return null;
	}

	@Override
	public void dropCache() {
		data = null;
	}

	@Override
	public void reload() {
		buildData();
	}
}
