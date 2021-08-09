package com.kang.kmall.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Kangshitao
 * @date 2021年8月2日 上午10:02
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登录检查逻辑
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if(user!=null){  //假设只要session中有登录用户就算，就放行
            return true;
        }
        //如果被拦截，就跳转到登录页面，并将错误信息返回
//        session.setAttribute("msg","请登录");
//        response.sendRedirect("/");

        request.setAttribute("msg","请登录"); //因为前端页面是从request域中取的msg的值，因此将msg信息放到请求域而不是session中
        request.getRequestDispatcher("/login").forward(request,response);
        return false;
    }

}
