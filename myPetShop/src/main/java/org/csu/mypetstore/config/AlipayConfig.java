package org.csu.mypetstore.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号,开发时使用沙箱提供的APPID，生产环境改成自己的APPID
    public static String APP_ID = "2016102400751828";

    //商户私钥
    public static String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCDwFShLADGca9TPrGlF0QZn6L1Y352GuOdeQFb2Ug1CUU5SmNM5WTYAJBqBxWUDFPXuw/kqtzuoOGxf6uxHoywqlyyslA29B9r23IKmfQ+31Ivj6eMDFUZZsCFR7CuQDkCV3Uj6o834RcpvrtJcFO0Xg0FqAA7sYCmG5iRGG3E03l+lJQr0MnXMkNyy8d6v/OJxfnMtOhw0hEDeEIqXwDmTUOBPHaouJ24xh6aJ8bqJ7rAV3QQfURnjfPbIbfFYFAFoOEWuQwcbMO1M5rWAOr98rQUAbwl4UzuxgO74D+H0vOKfZJkR9xZ9RHjmdGhdVhbTMYzn6V6fVKIuybuoM2lAgMBAAECggEAM8vYOajYASxOljaOlm3oKv60jS55WCtIEu+wlhB9L8SeP2C8W03P8I+ANv7j/9k82RJEtExsITCEqkicEj0FaKsBWIt7t68P71JNqldOS612XRZn80oh5qObyoodrtO21fHWLrGXbouClLnUXZHYho93JSMgPFeTUTqGJ3zoIrm+60vLeUcw6e9pGGy88DBcNNbOT6WRqsnjJH7UNPPH5hjCxlp09BFoWX/Rqq/LONK3LDqhah7DtHd5swpFEL/O9pvMdKif5Bo9LxNKeZp850BRVaZvJnUmH6Rp1xymVLBmOkfJ9YDIlpeFHpFXkZQqXbu4ttQEo3DAcTEt90hwPQKBgQDzFa8yR1ky8S2we7FNAB27WssyQCVYfs4PgerHSQp5QXolYUrRvSYQE/AibCI0tYphX/PiNLJ2HStYSCQcamTWSe2U+1vSrFsYuZC4CVRJV9/vNAoVxoZUEwB3gKnih0SiGD1zH6V6FbHe3ggp1a4nNjP33QLbgpxZQl3K7qHOCwKBgQCKwFhiK4Ctz0BZOBYVSDIeOAf5E0idzW+VTLaoVuH1tKfyfCMsA6TKpESSFqhh9dcB7kGr+5DNKl6my/QtNIV/sM2RPJvsvCCjd97saJwStmz7UwIawOI+kj1/9IXn+6Z2nrGgz2wfZ2KR61LRr9IpbPkGv86fgeKp/IjJssURDwKBgAeufL3TAV+ZvX7hOcYcJdRg1yD8Y/G6+BoHWfr4NOuGBS0fOPUNzCEf8Cu2TRJrVjcjiBpy3dzbqR3+EZ4QQqV+6pIVq14p8isC2i7mpJI4w6vUcikj/+SyQQaFu1icO6//ohl8O4dzHg7T52AjD4DPtq6zfj6fjv9HGJ2sEtLnAoGAc5xnSI008B4jYPPRurPoRrOrOQevVwVJN16vR771EjUQR4TWDaOPmOWc2RkV/mHIYCx0/edkNP9b1KFWeKAtqELaVmzpAy2SCIMl9VgIy7LDRfpZY49aNIbHxakqNizB/E6odPTAA76yLCGnZV6Zv7i0GsKRWOjxw2/PTjit6VMCgYANjnyhSZ9mkr7BFNw+4xgfCjJr6u+oAUFgiFyxPJXdkmLafYF6QgzHFDzAtb97B66aDwgVauKRrQ1H23aYhB+7TaTxx4nYkP5uaPqdHSfUPFowzhNnJAFhs2JkUWSki4bROQDE41KpJ7TPg7t42pb7b7VXYHulG3HIWd5QobONLg==";

    //支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj6obPPXZECXd7FaQTpFsUxNXkyFhngiOPhxaZrHj6RdhHLWAnH8BLFq5v0NFwVq9qFtZo050Cj+xxGOBo3nre1qdoqD7g1mwIr6nhY+s175DW+K8PbHN8p+sNQbwytLF/svLvVNjihfQD4uo8jBytymPOTz1U93PYmzx32Hu2RJDEf4PlGUD0lUo72EJpwyT+IF58FCVIRSiQQ4NzezrL1tO7GZZAdCI57F/A3cngxwqr3li5pdqEsJ09f4Lg0KQSQFbwgQnsp7gaVq5nyoMdv8NUje0f7fpWhf6BFoc7iLLK1vNnAfkZRL+AzVtyqfcUEM0513dRQeYqsWKxvjUJwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8080/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问(其实就是支付成功后返回的页面)
    public static String return_url = "http://3r9a4x.natappfree.cc/catalog/main";
    //http://localhost:8080/order/view_order?orderId=1013

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String CHARSET = "utf-8";

    // 支付宝网关，这是沙箱的网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 生成日志路径
    public static String log_path = "C:\\";

    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
