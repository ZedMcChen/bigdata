/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.citation.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhiming
 *
 */
public class DoiUtils {
    private static Pattern DoiPattern = Pattern.compile("[Dd][Oo][Ii](\\.org/|=\\s*?|:\\s*?|\\s*?)(?<doi>10\\.[0-9]{3,5}+/[^\\s\"\'\\&\\]\\}<>]+)", 
                                                         Pattern.CASE_INSENSITIVE);

    public static Set<String> findDois(String page) {
        Set<String> dois = new HashSet<>();
        Matcher matcher = DoiPattern.matcher(page);
        while (matcher.find()) {
            String doiStr = matcher.group("doi");
            doiStr = trimLastDot(doiStr);
            dois.add(doiStr.toLowerCase());
        }
        return dois;
    }

    private static String trimLastDot(String str) {
        if (str.length()>0 && str.charAt(str.length()-1) == '.') {
            str = str.substring(0, str.length()-1);
        }
        return str;
    }
}
