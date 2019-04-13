package com.bingdeng.sso.web.controller;

import com.bingdeng.sso.entity.User;
import com.bingdeng.sso.service.SSOLoginService;
import com.bingdeng.sso.utils.CookiesUtil;
import com.bingdeng.sso.utils.MD5Util;
import com.bingdeng.sso.utils.RedisCache;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by bingdeng on 2017/9/3.
 */
@Controller
public class SSOLoginController {

    private static Logger log = Logger.getLogger(SSOLoginController.class);

    @Autowired
    private SSOLoginService ssoLoginService;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView toLoginPage(@RequestParam(required = false)String callbackURL,
                                    HttpServletRequest req,
                                    HttpServletResponse resp){
        ModelAndView view = new ModelAndView("/ssopage/login/login");
        view.addObject("callbackURL",callbackURL);
        log.info("callbackURL:"+callbackURL);
        return view;
    }
    @RequestMapping(value = "/s500",method = RequestMethod.GET)
    public ModelAndView toErrorPage(){
        ModelAndView view = new ModelAndView("/error/500");
        return view;
    }

    @RequestMapping(value = {"/","/index"},method = RequestMethod.GET)
    public ModelAndView toIndexPage(HttpServletRequest request, HttpServletResponse response,HttpSession session){
        ModelAndView view = new ModelAndView("/ssopage/index/index");
        //获取cookie
        String cookieTicket = CookiesUtil.getCookieValueByName(request,"ssoToken");
        if(!StringUtils.isEmpty(cookieTicket)){
            String userNo = RedisCache.TICKET_AND_COOKIE.get(cookieTicket);
            User user = (User)session.getAttribute("user");
            if(StringUtils.isEmpty(userNo) || user == null || !userNo.equals(user.getUserNo())){
                view.setViewName("/ssopage/login/login");
            }
        }
        view.addObject("ticket",cookieTicket);
        return view;
    }

//    @RequestMapping("/sindex")
//    public ModelAndView toIndexPage(@RequestParam(required = false)String callbackURL,@RequestParam(required = false)String token){
//        ModelAndView view = new ModelAndView(callbackURL+"?token="+token);
//        return view;
//    }
    @RequestMapping(value = "/ssoLogin",method = RequestMethod.POST)
    public ModelAndView login(
            @RequestParam(required = false) String userNo,
            @RequestParam(required = false) String pwd,
            @RequestParam(required = false) String callbackURL,
            HttpServletRequest req,
            HttpServletResponse resp,
            HttpSession session
    ){
        ModelAndView view = new ModelAndView();
        try{
            boolean result = false;
            System.out.println("pwd = [" + MD5Util.encryptMD5(pwd) + "]");
            User user = ssoLoginService.ssoLogin(userNo, MD5Util.encryptMD5(pwd));

            if(user != null){
                //设置ticket
                String ticketString = UUID.randomUUID()+user.getUserNo();
                result = true;
                //缓存redis
                RedisCache.TICKET_AND_COOKIE.put(ticketString,user.getUserNo());
                session.setAttribute("user",user);
                //设置到cookie
                resp = CookiesUtil.setCookie(resp,"ssoToken",ticketString,30 * 60);
//                callbackURL="http://data.bingdeng.com:8080/uindex";
                log.info("callbackURL-login:"+callbackURL);
                //若无回调函数，则返回到首页
                if(!StringUtils.isEmpty(callbackURL)){
                    StringBuilder url = new StringBuilder();
                    url.append(callbackURL);
                    if (0 <= callbackURL.indexOf("?")) {
                        url.append("&");
                    } else {
                        url.append("?");
                    }
                    url.append("ticket=").append(ticketString);
                    resp.sendRedirect(url.toString());
                    return null;
                }else {
                    view.addObject("ticket",ticketString);
                    view.setViewName("/ssopage/index/index");
                }
            }else{
                view.setViewName("/ssopage/login/login");
                view.addObject("callbackURL",callbackURL);
                view.addObject("success",result);
                view.addObject("msg","用户名或密码错误");
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("登录异常:"+e.getMessage());
        }
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/verify",method = RequestMethod.POST)
    public Map<String,Object> verify(@RequestParam(required = false)String ticket, HttpSession session){
        Map<String,Object> map = new HashMap<String,Object>(1);
        map.put("ticketPass",false);
        String userNo = RedisCache.TICKET_AND_COOKIE.get(ticket);
        if(!StringUtils.isEmpty(ticket)){
            User user = (User)session.getAttribute("user");
            if(StringUtils.isEmpty(userNo) && user != null && userNo.equals(user.getUserNo())){
                map.put("ticketPass",true);
            }
        }
        return map;
    }

}
