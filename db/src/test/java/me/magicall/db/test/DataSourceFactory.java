package me.magicall.db.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import me.magicall.db.util.DataSourceTemplate;

public class DataSourceFactory {

	private static final DataSource DATA_SOURCE = new DataSourceImpl();

	public static DataSource getDataSource() {
		return DATA_SOURCE;
	}

	private static class DataSourceImpl extends DataSourceTemplate implements DataSource {
		private static final int MYSQL_PORT = 3306;
		private static final String DB_NAME = "test";
		private static final String DB_USER_NAME = "root";
		private static final String DB_USER_PSW = "123456";

		@Override
		public Connection getConnection() throws SQLException {
			return createConnection();
		}

		@Override
		public Connection getConnection(final String username, final String password) throws SQLException {
			return createConnection(username, password);
		}

		/**
		 * @return
		 * @throws SQLException
		 * @throws UnknownHostException
		 */
		private static Connection createConnection() throws SQLException {
			return createConnection(DB_USER_NAME, DB_USER_PSW);
		}

		private static Connection createConnection(final String userName, final String psw) throws SQLException {
			try {
				return createConnection(InetAddress.getLocalHost(), MYSQL_PORT, DB_NAME, userName, psw);
			} catch (final UnknownHostException e) {
				e.printStackTrace();
				throw new SQLException(e.getMessage());
			}
		}

		/**
		 * @return
		 * @throws SQLException
		 */
		private static Connection createConnection(final InetAddress address, final int port, final String dbName, final String userName, final String psw)
				throws SQLException {
			System.out.println("@@@@@@db ip:" + address.getHostAddress());
			return DriverManager.getConnection("jdbc:mysql://localhost" /* + address.getHostAddress() */+ ':'
					+ port + '/' + dbName//
					+ "?characterEncoding=gbk", userName, psw);
		}

//		@Override
		@Override
		public Logger getParentLogger() throws SQLFeatureNotSupportedException {
			// TODO Auto-generated method stub
			return null;
		}
	}

}
