package com.bingdeng.sso.interceptor;

import com.bingdeng.sso.utils.CookiesUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SSOInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri = request.getRequestURI();
        String path = request.getContextPath();
        String url = uri.substring(path.length());
        String ticket = request.getParameter("ticket");
        String callBackUrl = request.getParameter("callbackURL");
        System.out.println("url = [" + url + "]");
        //获取cookie
        String cookieTicket = CookiesUtil.getCookieValueByName(request,"ssoToken");
        //ticket不为空时，为子系统在效验是否登录
        if(StringUtils.isEmpty(callBackUrl) && !StringUtils.isEmpty(ticket) && !"null".equals(ticket)){
            //直接往下继续走
            return true;
        }
            //用户已经登录
            if(!StringUtils.isEmpty(cookieTicket)){
            //回调函数为空的情况
                if(StringUtils.isEmpty(callBackUrl)){
                    return super.preHandle(request,response,o);
                }
                //回调函数不为空
            StringBuilder url1 = new StringBuilder();
            url1.append(callBackUrl);
            if (0 <= callBackUrl.indexOf("?")) {
                url1.append("&");
            } else {
                url1.append("?");
            }
            url1.append("ticket=").append(cookieTicket);
            //重定向到要访问的系统
            response.sendRedirect(url1.toString());
            return false;
        }else if("/".equals(uri) || "/index".equals(uri)){
            //未登录时， 访问路径为"/" 或者 "/index"，重定向到登录页面
                response.sendRedirect(request.getContextPath()+"/login");
                return false;
        }
        //不满足以上其概况，则往下执行
        return super.preHandle(request,response,o);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        super.postHandle(httpServletRequest,httpServletResponse,o,modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        super.afterCompletion(httpServletRequest,httpServletResponse,o,e);
    }
}
