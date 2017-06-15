///**
// * @Title: GUIDUtils.java
// * @Package： com.appvworks.framework.Utils
// * @Description: TODO
// * @author：duanwei
// * @date： 2015年7月2日 上午10:58:48
// */
//package com.jovezhao.nest.core.utils;
//
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.UUID;
//
//public class GUIDUtils {
//
//    private static final String YYMMDDHHMMSS = "yyMMddHHmmss";
//
//    public static UUID getGUID() {
//        return UUID.randomUUID();
//    }
//
//
//    public static String getGUIDString() {
//        // 创建 GUID 对象
//        UUID uuid = getGUID();
//        // 得到对象产生的ID
//        String guid = uuid.toString();
//        // 转换为大写
//        guid = guid.toUpperCase();
//        // 替换 -
//        guid = guid.replaceAll("-", "");
//        return guid;
//    }
//
//
//    private static String getCurrentDate() {
//        return new SimpleDateFormat(YYMMDDHHMMSS).format(new Date());
//    }
//
//    /**
//     * 20位ID生成器，时间（年月日时分秒）+毫秒+5位随机数
//     *
//     * @return ID
//     */
//    public static String getGUID20() {
//        String strMills = Long.toString(System.currentTimeMillis() % 1000);
//        // 加入i，只是为了保险，防止出现死循环
//        int i = 0;
//        while ((strMills.length() < 3) && (i++ < 3)) {
//            strMills = "0" + strMills;
//        }
//        StringBuilder builder = new StringBuilder();
//        builder.append(getCurrentDate());
//
//        builder.append(strMills);
//        builder.append(RandomStringUtils.randomNumeric(5));
//        return builder.toString();
//    }
//
//    public static void main(String[] args) {
//
//        System.out.println(GUIDUtils.getGUID20());
//    }
//
//}
