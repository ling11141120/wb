package com.ling.note;

import cn.hutool.crypto.digest.DigestUtil;
import com.ling.note.dao.BaseDao;
import com.ling.note.dao.UserDao;
import com.ling.note.po.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:  LingWeiBo
 * @Date:  2025/6/7 09:21
 */
public class TestUser {
    @Test
    public void testQueryUserByName() {
        UserDao userDao = new UserDao();
        User user = userDao.queryUserByName("admin");
        System.out.println(user.getUpwd());
    }

    @Test
    public void testAdd() {
        String  sql = "insert into tb_user (uname,upwd ,nick,head,mood)values(?,?,?,?,?)";
        List<Object> params = new ArrayList<>();
        params.add("lisi");
        // 对密码进行 MD5 加密
        String password = DigestUtil.md5Hex("123456789");
        params.add(password);
        params.add("lisi");
        params.add("lisi.jpg");
        params.add("lisi");
        int update = BaseDao.executeUpdate(sql, params);
        System.out.println(update);
    }
}