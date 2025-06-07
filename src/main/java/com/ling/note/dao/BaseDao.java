package com.ling.note.dao;

import com.ling.note.util.DBUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LingWeiBo
 * @Date: 2025/6/7 10:56
 * 基础的jdbc类
 * 更新，查询操作
 */
public class BaseDao {

    // 更新操作（增删改）
    public static int executeUpdate(String sql, List<Object> params) {
        int row = 0;//受影响的行数
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (params != null && params.size() > 0) {
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }
            row = preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DBUtil.close(connection, null, preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return row;
    }

    // 查询单个值
    public static Object findSingleValue(String sql, List<Object> params) {
        Object obj = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (params != null && !params.isEmpty()) {
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                obj = resultSet.getObject(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DBUtil.close(connection, resultSet, preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    // 查询多行，多列，封装成对象集合
    public static List queryRows(String sql, List<Object> params, Class cls) {
        List list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            // 设置参数
            if (params != null && !params.isEmpty()) {
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }

            // 执行查询
            resultSet = preparedStatement.executeQuery();

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int fieldNUM = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                Object object = cls.newInstance();

                for (int i = 0; i < fieldNUM; i++) {
                    String columnName = resultSetMetaData.getColumnLabel(i + 1);
                    Field field = cls.getDeclaredField(columnName);
                    field.setAccessible(true);  // 允许访问private字段

                    // 反射调用对应的set方法
                    String setMethod = "set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                    Method method = cls.getDeclaredMethod(setMethod, field.getType());
                    Object value = resultSet.getObject(columnName);
                    method.invoke(object, value);
                }
                list.add(object);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | NoSuchMethodException |
                 NoSuchFieldException | InvocationTargetException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DBUtil.close(connection, resultSet, preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    // 查询单个对象
    public static Object queryPow(String sql, List<Object> params, Class cls) {
        List list = queryRows(sql, params, cls);
        Object object = null;
        if (!list.isEmpty()) {
            object = list.get(0);
        }
        return object;
    }
}
