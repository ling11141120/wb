package com.ling.note.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.ling.note.dao.UserDao;
import com.ling.note.po.User;
import com.ling.note.vo.ResultInfo;

/**
 * 用户服务类，处理用户相关业务逻辑
 */
public class UserService {

    private UserDao userDao = new UserDao();

    /**
     * 用户登录方法
     * @param uname 用户名
     * @param upwd 密码
     * @return 包含登录结果信息的 ResultInfo 对象
     */
    public ResultInfo<User> userLogin(String uname, String upwd) {
        ResultInfo<User> resultInfo = new ResultInfo<>();
        User u = new User();
        u.setUname(uname);
        u.setUpwd(upwd);
        resultInfo.setResult(u);

        if (StrUtil.isBlank(uname) || StrUtil.isBlank(upwd)) {
            resultInfo.setCode(0);
            resultInfo.setMsg("用户名或密码不能为空");
            return resultInfo;
        }

        User user = userDao.queryUserByName(uname);
        if (user == null) {
            resultInfo.setCode(0);
            resultInfo.setMsg("用户名不存在");
            return resultInfo;
        }

        // 对输入的密码进行 MD5 加密
        upwd = DigestUtil.md5Hex(upwd);

        if (upwd.equals(user.getUpwd())) {
            resultInfo.setCode(1);
            resultInfo.setMsg("登录成功");
            resultInfo.setResult(user);
            return resultInfo;
        } else {
            resultInfo.setCode(0);
            resultInfo.setMsg("密码错误");
            return resultInfo;
        }
    }
}