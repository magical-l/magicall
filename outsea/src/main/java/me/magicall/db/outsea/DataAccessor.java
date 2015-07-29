package me.magicall.db.outsea;

public interface DataAccessor<R, C extends SqlConfig> {

	C createSqlConfig(final String mainModelName);

	R exe(final C sqlConfig);
}
