//package com.jovezhao.nest.core.payment.alipay;
//
//
//import ILog;
//import LogAdapter;
//import com.jovezhao.nest.core.payment.PaymentProvider;
//import com.jovezhao.nest.core.payment.TradeOrder;
//import RSAEncrypt;
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Node;
//import org.springframework.web.client.RestTemplate;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.*;
//
///**
// * Created by Jove on 2016-02-22.
// */
//public class AlipayProvider implements PaymentProvider {
//    ILog logger = new LogAdapter(this.getClass());
//    private final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
//    private final String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKSLYVpfXXBrhNniS5jPWsRr853LVg7/l46nassffhC8XQXjLfokcp48J7eUlpFWOSsPL06uNT1f6Wt1wGttzEw4Qv+92Tks6MYhd68EwvWm3vXiiAlhHPVurb9OkwFbc91r5oNStmIrD2KAGUGLn8886/77AQplfA1wAghzLPkvAgMBAAECgYAyhooSUg5DIwDcVtyXw7Q/dFaOMY3vdRR4vXfRf44aXXzC/gt0MBTx+c7IVFwXrL5JNpR0OJIoknb+hF3dnw2IK/O3Pw5//WmgpJ4auw+bVd19Yd5VLVIJUELhzvT7dPmY2LqC+vG45QK54zJFHsC5PBI3xyXZyB70y5jkQJlXgQJBANOmipyVDXSpOua7UZWe7UtZr4aAXzq5CQRGwI77WjWub/ex22cFkJf6302okTYsMrGzNSwQ8vpMEPy3XrQTep8CQQDHBfOK7mRA50ix31S30zKSqz6IDHW+SmWX4/32NPUG3vLK68sDML6PUcqdS7EVhGdO+0VWrVG1D/fWmMXQ1odxAkEAuR0ojtsu8aIyCiw7pwlhsk2234TXxZyg7kSTlnrw45K2C4Zbxzdux/574IhWuXLew91h3DDW02REV0RqaEXBMQJABXUHUYbp3tG4r4/lHck/uL/TTh2xWM9na7vHqemX0mFonOHMRkEe6IUgniGGMIXZ41OGejL0a5eNfyffPLO1MQJAFLhI7B/1tbA/qeqzX27dje94RFMt2dH3KJ55VnScXqPc+V8uokiaBxGssIlkUjKrWH1pfxwA5zlcj7cstCiy6g==";
//    private final String partner = "2088911076904292";
//    private final String _input_charset = "utf-8";
//    private final String mapiGateway = "https://mapi.alipay.com/gateway.do";
//
//    @Override
//    public TradeOrder orderQuery(String orderId) {
//        TradeOrder order = new TradeOrder();
//        order.setOrderId(orderId);
//
//        RequestParame parames = new RequestParame("single_trade_query");
//        parames.put("out_trade_no", orderId);
//        try {
//            ResponseParame responseParame = request(parames);
//            if (responseParame.size() != 0) {
//                if ("TRADE_SUCCESS".equals(responseParame.get("trade_status")) || "TRADE_FINISHED".equals(responseParame.get("trade_status")))
//                    order.setOrderStatus(1);
//                order.setPrice(Double.parseDouble(responseParame.get("total_fee")));
//            }
//        } catch (DocumentException e) {
//            logger.warn(e.getMessage());
//        }
//        return order;
//    }
//
//    @Override
//    public String getPayParameter(String orderId, double price, int orderType, String title, String notifyUrl) {
//        return null;
//    }
//
//
//    private ResponseParame request(RequestParame parame) throws DocumentException {
//        String data = parame.getData();
//        String url = mapiGateway + "?" + data;
//        RestTemplate template = new RestTemplate();
//        String s = template.getForObject(url, String.class);
//        Document document = DocumentHelper.parseText(s);
//        Node node = document.selectSingleNode("/alipay/is_success");
//        ResponseParame responseParame = new ResponseParame();
//        if (node != null && "T".equals(node.getText())) {
//            //查询成功
//            List<Node> tradeNodes = document.selectNodes("/alipay/response/trade/*");
//            for (Node pNode : tradeNodes) {
//                responseParame.put(pNode.getName(), pNode.getText());
//            }
//        }
//        return responseParame;
//
//    }
//
//
//    /// <summary>
//    /// 请求参数
//    /// </summary>
//    class RequestParame extends HashMap<String, String> {
//
//        public RequestParame(String method) {
//            this.put("service", method);
//            this.put("partner", partner);
//            this.put("_input_charset", _input_charset);
//        }
//
//        public String getSign() {
//            String sourceSign = getSourceSign();
//
//
//            String sign = RSAEncrypt.sign(sourceSign.substring(0, sourceSign.length() - 1), privateKey, _input_charset);
//            return sign;
//        }
//
//        private String getSourceSign() {
//
//
//            ArrayList<String> list = new ArrayList<String>();
//            for (Map.Entry<String, String> entry : this.entrySet()) {
//                if (entry.getValue() != "") {
//                    list.add(entry.getKey() + "=" + entry.getValue() + "&");
//                }
//            }
//            int size = list.size();
//            String[] arrayToSort = list.toArray(new String[size]);
//            Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < size; i++) {
//                sb.append(arrayToSort[i]);
//            }
//            return sb.toString();
//
//
//        }
//
//        public String getData() {
//            return getSourceSign() + "sign_type=RSA&sign=" + getSign();
//        }
//
//
//    }
//
//    class ResponseParame extends HashMap<String, String> {
//
//    }
//
//    @Override
//    public String getCallbackTag() {
//        return "success";
//    }
//
//    @Override
//    public boolean isSuccessNotify(HttpServletRequest request) {
//
//        String trade = request.getParameter("trade_status");
//        String outNumber = request.getParameter("out_trade_no");
//        logger.warn("AlipayProvider",outNumber,trade);
//        if ("TRADE_SUCCESS".equals(trade) || "TRADE_FINISHED".equals(trade))
//            return true;
//        else
//            return false;
//    }
//}
