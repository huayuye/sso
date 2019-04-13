package com.bingdeng.sso.utils;

/*
 * 该类可以从浏览器请求中提取出cookies并进行对cookis的相关操作
 *
 */


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class CookiesUtil {

    /**
     * 根据名字删除cookie的Value
     *
     * @param request
     * @param name    cookie名字
     * @return
     */
    public static void removeCookieValueByName(HttpServletRequest request,HttpServletResponse response, String name) {
        Cookie cookie =  getCookieByName(request,name);
        if(cookie != null){
            cookie.setValue(null);
            cookie.setPath("/");
            // 参数为负值时表示 Cookie 的生存期和当前 Session 一样，一般是 30 分钟,
            // 参数为 0 时表示删除该 Cookie
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    /**
     * 根据名字修改cookie的Value
     *
     * @param request
     * @param name    cookie名字
     * @return
     */
    public static void editCookieValueByName(HttpServletRequest request,HttpServletResponse response, String name, String value) {
        Cookie cookie =  getCookieByName(request,name);
        if(cookie != null){
            cookie.setValue(value);
            cookie.setPath("/");
            cookie.setMaxAge(30* 60);// 设置为30min
            response.addCookie(cookie);
        }
    }

    /**
     * 根据名字获取cookie的Value
     *
     * @param request
     * @param name    cookie名字
     * @return
     */
    public static String getCookieValueByName(HttpServletRequest request, String name) {
              Cookie cookie =  getCookieByName(request,name);
              if(cookie != null){
                  return cookie.getValue();
              }
              return null;
    }

    /**
     * 根据名字获取cookie
     *
     * @param request
     * @param name    cookie名字
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    /**
     * 将cookie封装到Map里面
     *
     * @param request
     * @return
     */
    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 保存Cookies
     *
     * @param response servlet请求
     * @param value    保存值
     * @author jxf
     */
    public static HttpServletResponse setCookie(HttpServletResponse response, String name, String value, int time) {
        // new一个Cookie对象,键值对为参数
        Cookie cookie = new Cookie(name, value);
        // tomcat下多应用共享
        cookie.setPath("/");
        cookie.setDomain(".bingdeng.com");
        // 如果cookie的值中含有中文时，需要对cookie进行编码，不然会产生乱码
        try {
            URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        cookie.setMaxAge(time);
        // 将Cookie添加到Response中,使之生效
        response.addCookie(cookie); // addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
        return response;
    }
}