package com.ling.note.dao;

import com.ling.note.po.User;
import com.ling.note.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用户数据访问对象
 */
public class UserDao {

    /**
     * 根据用户名查询用户信息
     * @param uname 用户名
     * @return 用户对象，如果未找到则返回 null
     */
    public User queryUserByName(String uname) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from tb_user where uname = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, uname);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUname(resultSet.getString("uname"));
                // 确保数据库字段名和实体类属性名一致
                user.setUpwd(resultSet.getString("upwd"));
                user.setNick(resultSet.getString("nick"));
                user.setHead(resultSet.getString("head"));
                user.setMood(resultSet.getString("mood"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DBUtil.close(connection, resultSet, preparedStatement);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }
}