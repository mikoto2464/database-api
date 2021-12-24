package net.mikoto.dao;

import java.sql.*;

/**
 * @author mikoto
 * @date 2021/11/28 14:45
 */
public abstract class BaseDao {
    private final String url;
    private final String userName;
    private final String userPassword;
    private Connection connection;
    private PreparedStatement preparedStatement;

    protected BaseDao(String url, String userName, String userPassword) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.url = url;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * Get the connection.
     * If the connection is null, it will create one.
     *
     * @throws SQLException SQLException.
     */
    protected Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url,
                    userName,
                    userPassword);
        }
        return connection;
    }

    /**
     * Query data in database.
     *
     * @param sql SQL statement.
     * @return A result set.
     * @throws SQLException SQLException
     */
    protected ResultSet executeQuery(String sql) throws SQLException {
        getConnection();
        preparedStatement = connection.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }

    /**
     * Close all the resource.
     */
    protected void closeResource() {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
