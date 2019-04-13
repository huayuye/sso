package com.bingdeng.sso.filter;

import com.bingdeng.sso.utils.CookiesUtil;
import com.bingdeng.sso.utils.RedisCache;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by bingdeng on 2017/8/28.
 */
@WebFilter(filterName = "LoginFilter")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest hreq = (HttpServletRequest)req;
        HttpServletResponse hresp = (HttpServletResponse)resp;
        String uri = hreq.getRequestURI();
        String path = hreq.getContextPath();
        String url = uri.substring(path.length());
        String token = hreq.getParameter("ticket");
        String callBackUrl = hreq.getParameter("callbackURL");
        System.out.println("url = [" + url + "]");
        boolean isResource = !url.endsWith(".js") && !url.endsWith(".css") && !url.endsWith(".jpg") && !url.endsWith(".png");
        boolean isUrl = !("/s500").equals(url) && !"/login".equals(url) &&!"/ssoLogin".equals(url);
        String cookieValue = "";
//        if(isResource && isUrl ){
        //获取cookie
            cookieValue = CookiesUtil.getCookieValueByName(hreq,"ssoToken");
        //ticket不为空时，为子系统在效验是否登录
            if(StringUtils.isEmpty(callBackUrl) && !StringUtils.isEmpty(token)){
                chain.doFilter(req,resp);
                return;
            }
            //用户已经登录
            if(!StringUtils.isEmpty(cookieValue)){
//                String tokenString = cookieValue + System.currentTimeMillis();
//                RedisCache.TICKET_AND_COOKIE.put(tokenString,cookieValue);
                StringBuilder url1 = new StringBuilder();
                url1.append(callBackUrl);
                if (0 <= callBackUrl.indexOf("?")) {
                    url1.append("&");
                } else {
                    url1.append("?");
                }
                url1.append("ticket=").append(cookieValue);
                hresp.sendRedirect(url.toString());
            }else{
                chain.doFilter(req, resp);
            }
//        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
