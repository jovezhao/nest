package com.ywkj.nest.core.payment.weixin;

/**
 * Created by Jove on 2016-02-23.
 */
public class WeixinConfig {
    static WeixinConfig doctorConfig;
    static WeixinConfig motherConfig;
    public static WeixinConfig getDoctorConfig()
    {
            if (doctorConfig == null)
            {
                doctorConfig = new WeixinConfig();
                doctorConfig.appid = "wx6a2abe280399cd66";
                doctorConfig.key = "51c56b886b5be869567dd389b3e5d1d8";
                doctorConfig.mch_id = "1310478701";
            }
            return doctorConfig;
    }

    public static WeixinConfig getMotherConfig()
    {

            if (motherConfig == null)
            {
                motherConfig = new WeixinConfig();
                motherConfig.appid = "wxc6104deae2404dd7";
                motherConfig.key = "51c56b886b5be869567dd389b3e5d1d9";
                motherConfig.mch_id = "1310455501";
            }
            return motherConfig;
    }

    private WeixinConfig() { }
    private String appid;
    private String mch_id ;
    private String key ;


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
