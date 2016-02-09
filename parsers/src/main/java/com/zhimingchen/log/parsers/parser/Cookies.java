/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.log.parsers.parser;

import java.util.HashMap;
import java.util.Map;
import static java.net.URLDecoder.decode;

import java.io.UnsupportedEncodingException;
/**
 * @author zhiming
 *
 */
public class Cookies {
    private Map<String, String> cookieMap;
    
    private Cookies() {
        this.cookieMap = new HashMap<>();
    }
    
    public int size() {
        return cookieMap.size();
    }
    
    public String getCookie(String name) {
        if (cookieMap.containsKey(name)) {
            return cookieMap.get(name);
        } else {
            return "COOKIE_NOT_FOUND";
        }
    }
    
    @Override
    public String toString() {
        return cookieMap.toString();
    }
    
    public static Cookies parse(String cookieString) {
        Cookies cookies = new Cookies();
        for (String aCookie: cookieString.split(";\\s+")) {
            String[] substr = aCookie.split("=");
            if (substr.length == 2) {
                try {
                    cookies.cookieMap.put(substr[0], decode(substr[1], "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return cookies;
    }

}
