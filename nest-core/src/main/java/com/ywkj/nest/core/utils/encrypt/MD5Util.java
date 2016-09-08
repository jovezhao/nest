package com.ywkj.nest.core.utils.encrypt;

import com.ywkj.nest.core.log.ILog;
import com.ywkj.nest.core.log.LogAdapter;

import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * @author xiangxj
 * @version 1.0
 * @ClassName: MD5Util
 * @Description: 采用MD5加密
 * @date 2015年11月12日 下午1:45:59
 */
public class MD5Util {
    private  static ILog logger=new LogAdapter(MD5Util.class);
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 转换字节数组为16进制字串
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }

    /**
     * 转换byte到16进制
     * @param b 要转换的byte
     * @return 16进制格式
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * MD5编码
     * @param origin 原始字符串
     * @return 经过MD5加密之后的结果
     */
    public static String md5(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes(Charset.forName("utf-8"))));
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        return resultString;
    }


}