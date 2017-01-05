package com.ywkj.nest.core.payment.weixin;


import com.ywkj.nest.core.exception.GeneralException;
import com.ywkj.nest.core.payment.PaymentProvider;
import com.ywkj.nest.core.payment.TradeOrder;
import com.ywkj.nest.core.utils.JSONTools;
import com.ywkj.nest.core.utils.encrypt.MD5Util;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import com.ywkj.nest.core.log.ILog;
import com.ywkj.nest.core.log.LogAdapter;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by Jove on 2016-02-23.
 */
public class WeixinProvider implements PaymentProvider {
    ILog logger=new LogAdapter(this.getClass());

    WeixinConfig config;

    public WeixinProvider(WeixinConfig config) {
        this.config = config;
    }

    private final String unifiedorderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    private final String orderqueryUrl = "https://api.mch.weixin.qq.com/pay/orderquery";

    public TradeOrder orderQuery(String orderId) {
        TradeOrder order = new TradeOrder();
        order.setOrderId(orderId);
        RequestParame parames = new RequestParame(config);
        parames.put("out_trade_no", orderId);
        parames.put("mch_id", config.getMch_id());
        parames.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));

        ResponseParame response = null;
        try {
            response = request(orderqueryUrl, parames);
        } catch (IOException e) {
            logger.warn(e.getMessage());
            response = new ResponseParame();
        }
        if ("SUCCESS".equals(response.get("return_code"))) {
            if ("SUCCESS".equals(response.get("result_code"))) {


                if ("SUCCESS".equals(response.get("trade_state"))) {
                    order.setPrice(Integer.parseInt(response.get("total_fee")) / 100.00d);
                    order.setOrderStatus(1);
                } else {
                    order.setOrderStatus(0);
                    order.setPrice(0);
                }
            } else {
                order.setOrderStatus(3);
            }
        } else {
            throw new GeneralException("查询发生异常");
        }
        return order;
    }

    private ResponseParame request(String url, RequestParame parame) throws IOException {
        ResponseParame req = new ResponseParame();


        String data = parame.getData();




        HttpPost httpPost = new HttpPost(url);
        StringEntity postEntity = new StringEntity(data, Charset.forName("utf-8"));

        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        HttpClient client = new DefaultHttpClient();

        HttpResponse response = client.execute(httpPost);


        org.apache.http.HttpEntity entity = response.getEntity();

        String result = EntityUtils.toString(entity, "UTF-8");

        Document document = null;
        try {
            document = DocumentHelper.parseText(result);
        } catch (DocumentException e) {
            logger.warn(e.getMessage());
            return req;
        }
        List<Node> nodes = document.selectNodes("/xml/*");

        for (Node nd : nodes) {
            req.put(nd.getName(), nd.getText());
        }
        return req;
    }

    public String getPayParameter(String orderId, double price, int orderType, String title, String notifyUrl) {
        TradeOrder order = new TradeOrder();
        order.setOrderId(orderId);
        RequestParame parames = new RequestParame(config);
        parames.put("body", title);
        parames.put("mch_id", config.getMch_id());
        parames.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
        parames.put("out_trade_no", orderId);
        parames.put("total_fee", String.valueOf((int) (price * 100)));
        parames.put("spbill_create_ip", "8.8.8.8");
        parames.put("notify_url", notifyUrl);
        parames.put("trade_type", "APP");

        ResponseParame response = null;
        try {
            response = request(unifiedorderUrl, parames);
        } catch (IOException e) {
            logger.warn(e.getMessage());
            response = new ResponseParame();
        }

        if ("SUCCESS".equals(response.get("return_code"))) {
            if ("SUCCESS".equals(response.get("result_code"))) {
                String prepay_id = response.get("prepay_id");

                RequestParame payParame = new RequestParame(config);
                payParame.put("noncestr", UUID.randomUUID().toString().replace("-", ""));
                payParame.put("prepayid", prepay_id);
                payParame.put("partnerid", config.getMch_id());
                payParame.put("package", "Sign=WXPay");
                payParame.put("timestamp", "1453888544");//String.valueOf(System.currentTimeMillis() / 1000));

                String json = payParame.getJson();
                return json;

            } else {
                return "";
            }
        } else {
            throw new GeneralException("发起支付出错");
        }
    }

    public String getCallbackTag() {
        return "<xml>" +
                "                < return_code ><![CDATA[SUCCESS]]></return_code >" +
                "        <return_msg ><![CDATA[OK]]></return_msg >" +
                "</xml > ";
    }

    public boolean isSuccessNotify(HttpServletRequest request) {
        try {
            Document document = new SAXReader().read(request.getInputStream());
            this.logger.warn(document.asXML());
        } catch (DocumentException e) {
            logger.warn(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
        return true;
    }

    class RequestParame extends HashMap<String, String> {
        private String key;

        public RequestParame(WeixinConfig config) {
            this.put("appid", config.getAppid());
            this.key = config.getKey();
        }

        private String getSourceSign() {


            ArrayList<String> list = new ArrayList<String>();
            for (Map.Entry<String, String> entry : this.entrySet()) {
                if (entry.getValue() != "") {

                    list.add(entry.getKey() + "=" + entry.getValue() + "&");

                    //list.add(entry.getKey() + "=" + URLEncrypt.encode(entry.getValue()) + "&");
                }
            }
            int size = list.size();
            String[] arrayToSort = list.toArray(new String[size]);
            Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append(arrayToSort[i]);
            }
            return sb.toString();

        }

        public String getData() {
            Document document = DocumentHelper.createDocument();
            Element root = DocumentHelper.createElement("xml");
            for (Map.Entry<String, String> p : this.entrySet()) {
                Element cp = DocumentHelper.createElement(p.getKey());
                //cp.setText(p.getValue());
                cp.addCDATA(p.getValue());
                //cp.setText(URLEncrypt.encode(p.getValue()));
                root.add(cp);
            }
            Element sp = DocumentHelper.createElement("sign");
            sp.setText(getSign());
            root.add(sp);
            document.add(root);
            return document.asXML();
        }

        public String getJson() {
            HashMap<String, String> result = new HashMap<String, String>(this);


            result.put("sign", getSign());
            return JSONTools.toJsonString(result);
        }

        public String getSign() {
            String sourceSign = getSourceSign();
            String sign = MD5Util.md5(sourceSign + "key=" + key).toUpperCase();
            return sign;


        }


    }

    class ResponseParame extends HashMap<String, String> {

    }
}
