package org.mheguevara.restsimulator.dao;

import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: muhammet
 * Date: 9/23/12
 * Time: 4:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class DbUtil {

    private static final Logger logger = Logger.getLogger(DbUtil.class);

    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(true);
        return connection;
    }

    public Connection getConnection(boolean autoCommit) throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(autoCommit);
        return connection;
    }

    public void close(Connection connection)  {
        try {
            connection.close();
        } catch (Exception e) {
            logger.warn(e,e);
        }
    }

    public void rollback(Connection connection)  {
        try {
            connection.rollback();
        } catch (Exception e) {
            logger.warn(e,e);

        }
    }

    public void close(PreparedStatement preparedStatement) {
        try {
            preparedStatement.close();
        } catch (Exception e) {
            logger.warn(e,e);
        }
    }

    public void close(ResultSet resultSet)  {
        try {
            resultSet.close();
        } catch (Exception e) {
            logger.warn(e,e);
        }
    }
}
