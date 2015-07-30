package me.magicall.db.outsea.springjdbc;

import me.magicall.db.outsea.SqlConfig;
import me.magicall.db.outsea.springjdbc.SqlBuilder.ParamedSqlAndParams;

public interface DataAccessTrigger<R, C extends SqlConfig> {

	void before(C sqlConfig);

	void beforeExe(C sqlConfig, ParamedSqlAndParams paramedSqlAndParams);

	void afterExe(R result, C sqlConfig);

	class DataAccessTriggerAdaptor<R, C extends SqlConfig> implements DataAccessTrigger<R, C> {

		@Override
		public void before(final C sqlConfig) {
		}

		@Override
		public void beforeExe(final C sqlConfig, final ParamedSqlAndParams paramedSqlAndParams) {
		}

		@Override
		public void afterExe(final R result, final C sqlConfig) {
		}

		private static DataAccessTriggerAdaptor<Object, SqlConfig> EMPTY = new DataAccessTriggerAdaptor<>();

		@SuppressWarnings("unchecked")
		public static <R, C extends SqlConfig> DataAccessTriggerAdaptor<R, C> empty() {
			return (DataAccessTriggerAdaptor<R, C>) EMPTY;
		}
	}
}
