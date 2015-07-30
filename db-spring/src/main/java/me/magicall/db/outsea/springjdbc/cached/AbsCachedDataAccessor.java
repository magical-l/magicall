package me.magicall.db.outsea.springjdbc.cached;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import me.magicall.db.outsea.DataAccessor;
import me.magicall.db.outsea.SqlConfig;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public abstract class AbsCachedDataAccessor<R, C extends SqlConfig> implements DataAccessor<R, C> {

	@SuppressWarnings("unchecked")
	protected final LoadingCache<C, R> cache//
	= expireAfterWrite((CacheBuilder) CacheBuilder.newBuilder())//
			.build(new CacheLoader<C, R>() {
				@Override
				public R load(final C key) throws Exception {
					return wrapped().exe(key);
				}
			});

	@Override
	public C createSqlConfig(final String mainModelName) {
		return wrapped().createSqlConfig(mainModelName);
	}

	@Override
	public R exe(final C sqlConfig) {
		try {
			return cache.get(sqlConfig);
		} catch (final ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected CacheBuilder<C, R> expireAfterWrite(final CacheBuilder<C, R> cacheBuilder) {
		return cacheBuilder.expireAfterAccess(expireMillsecondAfterWrite(), TimeUnit.MILLISECONDS);
	}

	protected long expireMillsecondAfterWrite() {
		return TimeUnit.MINUTES.toMillis(30);
	}

	protected abstract DataAccessor<R, C> wrapped();

	protected void clear() {
		cache.invalidateAll();
	}
}
