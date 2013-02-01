package com.soulgalore.jdbcmetrics.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

public class StatementWrapper implements Statement {

	private final Statement st;
	public StatementWrapper(final Statement st) {
		super();
		this.st = st;
	}
	public void addBatch(String sql) throws SQLException {
		st.addBatch(sql);
	}
	public void cancel() throws SQLException {
		st.cancel();
	}
	public void clearBatch() throws SQLException {
		st.clearBatch();
	}
	public void clearWarnings() throws SQLException {
		st.clearWarnings();
	}
	public void close() throws SQLException {
		st.close();
	}
	public boolean execute(String sql, int autoGeneratedKeys)
			throws SQLException {
		return st.execute(sql, autoGeneratedKeys);
	}
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		return st.execute(sql, columnIndexes);
	}
	public boolean execute(String sql, String[] columnNames)
			throws SQLException {
		return st.execute(sql, columnNames);
	}
	public boolean execute(String sql) throws SQLException {
		return st.execute(sql);
	}
	public int[] executeBatch() throws SQLException {
		return st.executeBatch();
	}
	public ResultSet executeQuery(String sql) throws SQLException {
		return st.executeQuery(sql);
	}
	public int executeUpdate(String sql, int autoGeneratedKeys)
			throws SQLException {
		return st.executeUpdate(sql, autoGeneratedKeys);
	}
	public int executeUpdate(String sql, int[] columnIndexes)
			throws SQLException {
		return st.executeUpdate(sql, columnIndexes);
	}
	public int executeUpdate(String sql, String[] columnNames)
			throws SQLException {
		return st.executeUpdate(sql, columnNames);
	}
	public int executeUpdate(String sql) throws SQLException {
		return st.executeUpdate(sql);
	}
	public Connection getConnection() throws SQLException {
		return st.getConnection();
	}
	public int getFetchDirection() throws SQLException {
		return st.getFetchDirection();
	}
	public int getFetchSize() throws SQLException {
		return st.getFetchSize();
	}
	public ResultSet getGeneratedKeys() throws SQLException {
		return st.getGeneratedKeys();
	}
	public int getMaxFieldSize() throws SQLException {
		return st.getMaxFieldSize();
	}
	public int getMaxRows() throws SQLException {
		return st.getMaxRows();
	}
	public boolean getMoreResults() throws SQLException {
		return st.getMoreResults();
	}
	public boolean getMoreResults(int current) throws SQLException {
		return st.getMoreResults(current);
	}
	public int getQueryTimeout() throws SQLException {
		return st.getQueryTimeout();
	}
	public ResultSet getResultSet() throws SQLException {
		return st.getResultSet();
	}
	public int getResultSetConcurrency() throws SQLException {
		return st.getResultSetConcurrency();
	}
	public int getResultSetHoldability() throws SQLException {
		return st.getResultSetHoldability();
	}
	public int getResultSetType() throws SQLException {
		return st.getResultSetType();
	}
	public int getUpdateCount() throws SQLException {
		return st.getUpdateCount();
	}
	public SQLWarning getWarnings() throws SQLException {
		return st.getWarnings();
	}
	public boolean isClosed() throws SQLException {
		return st.isClosed();
	}
	public boolean isPoolable() throws SQLException {
		return st.isPoolable();
	}
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return st.isWrapperFor(iface);
	}
	public void setCursorName(String name) throws SQLException {
		st.setCursorName(name);
	}
	public void setEscapeProcessing(boolean enable) throws SQLException {
		st.setEscapeProcessing(enable);
	}
	public void setFetchDirection(int direction) throws SQLException {
		st.setFetchDirection(direction);
	}
	public void setFetchSize(int rows) throws SQLException {
		st.setFetchSize(rows);
	}
	public void setMaxFieldSize(int max) throws SQLException {
		st.setMaxFieldSize(max);
	}
	public void setMaxRows(int max) throws SQLException {
		st.setMaxRows(max);
	}
	public void setPoolable(boolean poolable) throws SQLException {
		st.setPoolable(poolable);
	}
	public void setQueryTimeout(int seconds) throws SQLException {
		st.setQueryTimeout(seconds);
	}
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return st.unwrap(iface);
	}

}