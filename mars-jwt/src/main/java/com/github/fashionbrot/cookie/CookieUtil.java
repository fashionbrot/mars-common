package com.github.fashionbrot.cookie;

import com.github.fashionbrot.ObjectUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author fashi
 */
public class CookieUtil {

    /**
     * 增加cookie
     * @param response          response
     * @param responseCookie    responseCookie
     */
    public static void addCookie(HttpServletResponse response , ResponseCookie responseCookie){
        if (responseCookie!=null){
            response.addHeader(ResponseCookie.SET_COOKIE,responseCookie.toString());
        }
    }

    /**
     * 增加cookie
     * @param response          response
     * @param responseCookie    responseCookie list
     */
    public static void addCookie(HttpServletResponse response , List<ResponseCookie> responseCookie){
        if (ObjectUtil.isNotEmpty(responseCookie)){
            responseCookie.forEach(c->{
                if (c!=null){
                    response.addHeader(ResponseCookie.SET_COOKIE,c.toString());
                }
            });
        }
    }

    /**
     * 增加cookie
     * @param response          response
     * @param responseCookie    responseCookie[]
     */
    public static void addCookie(HttpServletResponse response , ResponseCookie... responseCookie){
        if (ObjectUtil.isNotEmpty(responseCookie)){
            for (int i = 0; i < responseCookie.length; i++) {
                if (responseCookie[i]!=null) {
                    response.addHeader(ResponseCookie.SET_COOKIE, responseCookie[i].toString());
                }
            }
        }
    }


    /**
     * 获取 cookie
     * @param request       request
     * @param cookieName    cookieName
     * @return String
     */
    public static String getCookieValue(HttpServletRequest request ,String cookieName){
        Cookie[] cookies = request.getCookies();
        return getCookieValue(cookies,cookieName);
    }

    /**
     * 获取 cookie
     * @param cookieList        cookieList
     * @param cookieName        cookieName
     * @return String
     */
    public static String getCookieValue(Cookie[] cookieList, String cookieName ) {
        if (ObjectUtil.isNotEmpty(cookieList) && ObjectUtil.isNotEmpty(cookieName)){
            for (int i = 0; i < cookieList.length; i++) {
                if (cookieList[i].getName().equals(cookieName)) {
                    return cookieList[i].getValue();
                }
            }
        }
        return "";
    }





}
