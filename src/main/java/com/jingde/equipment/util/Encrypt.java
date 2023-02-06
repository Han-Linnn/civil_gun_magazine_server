package com.jingde.equipment.util;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具
 *
 * @author
 */
public class Encrypt {
    private static Logger logger = LoggerFactory.getLogger(Encrypt.class);
    //  静态加盐
    private static final String SALT_STRING = "be5e0323a9195ade";

    public static String encryptByMd5(String rawString) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 加盐
            String mergeString = rawString + SALT_STRING;
            byte[] md5Bytes = md.digest(mergeString.getBytes());
            result = Hex.encodeHexString(md5Bytes);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }
}
