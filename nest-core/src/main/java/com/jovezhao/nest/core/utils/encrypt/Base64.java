package com.jovezhao.nest.core.utils.encrypt;

import com.jovezhao.nest.core.log.ILog;
import com.jovezhao.nest.core.log.LogAdapter;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;

/**
 * Created by Jove on 2016-02-22.
 */
public class Base64 {
    private  static ILog logger=new LogAdapter(Base64.class);
    // 加密
    public static String encode(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.warn(e.getMessage());
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    // 解密
    public static String decode(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                logger.warn(e.getMessage());
            }
        }
        return result;
    }
}
