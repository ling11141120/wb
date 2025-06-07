package com.ling.note.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Properties;

/**
 * &#064;Author:  LingWeiBo
 * &#064;Date:  2025/6/6 16:17
 */
public class DBUtil {
    //得到配置对象
    private static Properties properties = new Properties();

    static {
        //加载配置文件
        InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
        //将输入流中的内容加载到配置文件对象心中
        try {
            properties.load(in);
            Class.forName(properties.getProperty("jdbcName"));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取数据库链接
     */
    public static Connection getConnection() throws SQLException {

        Connection connection = null;
        //得到数据库链接的相关信息
        String dbUrl = properties.getProperty("dbUrl");
        String dbName = properties.getProperty("dbName");
        String dbPwd = properties.getProperty("dbPwd");
        //获取数据库链接
        connection = DriverManager.getConnection(dbUrl, dbName, dbPwd);
        return connection;
    }

    public static void close(Connection connection, ResultSet resultSet, PreparedStatement preparedStatement) throws SQLException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}

