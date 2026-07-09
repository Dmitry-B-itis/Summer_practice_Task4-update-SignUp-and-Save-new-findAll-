package ru.itis.shop.infrastructure.jdbc;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class DriverManagerDataSource implements DataSource {

    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;

    private Connection connection = null;

    public DriverManagerDataSource(String DB_URL, String DB_USER, String DB_PASSWORD) {
        this.DB_URL = DB_URL;
        this.DB_USER = DB_USER;
        this.DB_PASSWORD = DB_PASSWORD;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            makeConnection();
        }

        return connection;
    }

    private void makeConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new RuntimeException("Not implemented exception");
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new RuntimeException("Not implemented exception");
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new RuntimeException("Not implemented exception");
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new RuntimeException("Not implemented exception");
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new RuntimeException("Not implemented exception");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new RuntimeException("Not implemented exception");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new RuntimeException("Not implemented exception");
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new RuntimeException("Not implemented exception");
    }

}
