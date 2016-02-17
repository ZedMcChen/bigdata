/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.citation.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author zhiming
 *
 */
public class Md5Utils {
    public static String getMD5(String input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            BigInteger digestNumber = new BigInteger(1, digest);
            String digestHex = digestNumber.toString(16);
            while (digestHex.length() < 32) {
                digestHex = "0" + digestHex;
            }
            return digestHex;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
