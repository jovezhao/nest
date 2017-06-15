package com.jovezhao.nest.core.utils.encrypt;


import com.jovezhao.nest.core.log.ILog;
import com.jovezhao.nest.core.log.LogAdapter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Jove on 2016-02-23.
 */
public class URLEncrypt {
    private  static ILog logger=new LogAdapter(RSAEncrypt.class);
    public static String encode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.warn(e.getMessage());
            return str;
        }
    }

    public static String decode(String str) {
        try {
            return URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.warn(e.getMessage());
            return str;
        }
    }
}
