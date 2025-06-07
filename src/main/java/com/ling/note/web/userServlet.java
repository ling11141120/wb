package com.ling.note.web;

import com.ling.note.po.User;
import com.ling.note.service.UserService;
import com.ling.note.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * &#064;Author:  LingWeiBo
 * &#064;Date:  2025/6/7 09:04
 */
@WebServlet("/user")
public class userServlet extends HttpServlet {
private UserService userService=new UserService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         String actionName=req.getParameter("actionName");
         if("login".equals(actionName)) {
             userLogin(req, resp);
         }
    }

    private void userLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userName=req.getParameter("uname");
        String userPwd=req.getParameter("upwd");
        ResultInfo<User> resultInfo=userService.userLogin(userName,userPwd);
        if (resultInfo.getCode()==1){
            req.getSession().setAttribute("user",resultInfo.getResult());
            String rem=req.getParameter("rem");
            if ("1".equals(rem)){
                Cookie cookie=new Cookie("user",userName+","+userPwd);
                cookie.setMaxAge(60*60*24*7);
                resp.addCookie(cookie);
            }else{
               Cookie cookie=new Cookie("user",null);
               cookie.setMaxAge(0);
               resp.addCookie(cookie);
            }
            resp.sendRedirect("/index.jsp");
        }else {
            req.getSession().setAttribute("resultInfo",resultInfo);
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }
    }


}
