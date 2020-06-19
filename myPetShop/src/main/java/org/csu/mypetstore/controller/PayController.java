package org.csu.mypetstore.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.csu.mypetstore.config.AlipayConfig;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.domain.Payment;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

@Controller
public class PayController {

    @Autowired
    OrderService orderService;

    //未填写
    @RequestMapping("/order/final_confirm_order")
    public void payController(HttpServletRequest request, HttpServletResponse response, HttpSession session, Payment payment, Order order, Model model) throws IOException
    {
        //初始化AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        Order order1 = orderService.generateOrder(session, payment, order);
        session.setAttribute("orderId",order1.getOrderId());
        model.addAttribute("order",order);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = String.valueOf(order1.getOrderId());
        //付款金额，必填
        String total_amount = String.valueOf(order1.getTotalPrice().multiply(new BigDecimal("7")));
        //订单名称，必填
        String subject = String.valueOf(order1.getOrderId());
        //商品描述，可空
        String body = null;

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
        response.getWriter().write(form);//直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
        orderService.confirmOrder(session);
    }
}
