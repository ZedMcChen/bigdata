/**
 * Copyright Zhiming Chen 2016
 */
package com.zedmcchen.log.parsers.utils;

import org.apache.commons.io.FilenameUtils;

/**
 * @author zhiming
 *
 */
public class JarLocator {
    public static String findJar(Class claz) {
        String className = "/" + claz.getName().replace(".", "/") + ".class";
        String jarPath = claz.getResource(className).getPath().split("!")[0];
        String jarName = FilenameUtils.getName(jarPath);
        return jarName;
    }
}
