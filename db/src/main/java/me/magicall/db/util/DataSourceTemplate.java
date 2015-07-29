package me.magicall.db.util;

import java.io.PrintWriter;
import java.sql.SQLException;

import javax.sql.DataSource;

public abstract class DataSourceTemplate implements DataSource {
	@Override
	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	@Override
	public void setLoginTimeout(final int seconds) throws SQLException {
		throw new UnsupportedOperationException("DataSource#setLoginTimeout not supported.");
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		throw new UnsupportedOperationException("DataSource#getLogWriter not supported.");
	}

	@Override
	public void setLogWriter(final PrintWriter out) throws SQLException {
		throw new UnsupportedOperationException("DataSource#setLogWriter not supported.");
	}

	@Override
	public boolean isWrapperFor(final Class<?> iface) throws SQLException {
		return DataSource.class.equals(iface);
	}

	@Override
	public <T> T unwrap(final Class<T> iface) throws SQLException {
		if (!DataSource.class.equals(iface)) {
			throw new SQLException("DataSource [" + getClass().getName() + "] can't be unwrapped as [" + iface.getName() + "].");
		}
		return iface.cast(this);
	}
}
