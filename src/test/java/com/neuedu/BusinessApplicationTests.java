package com.neuedu;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BusinessApplicationTests {

	@Test
	public void contextLoads() {
//		Long orderId=10234141231312l;
//		BigDecimal money=new BigDecimal(18);
//		JSONObject data=new JSONObject();
//		HttpServletResponse response=null;
//		int subject=1;
//		//订单号,必填
//		data.put("out_trade_no", orderId);
//		//PC支付 FAST_INSTANT_TRADE_PAY, APP支付 QUICK_MSECURITY_PAY, 移动H5支付 QUICK_WAP_PAY
//		data.put("product_code","FAST_INSTANT_TRADE_PAY");
//		//付款金额,必填
//		data.put("total_amount", money);
//		//订单描述,必填
//		if(subject==0)
//			data.put("subject","充值业务");
//		//该笔订单允许的最晚付款时间，逾期将关闭交易
//		//data.put("timeout_express","");
//		//公共校验参数
//		//data.put("passback_params","");
//		//PC支付
//		AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
//		//APP支付
//		//AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
//		//移动H5支付
//		//AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
//		//异步通知地址
//		request.setNotifyUrl("http://localhost:8765/pay/alipay/notify");
//		//同步通知地址
//		request.setReturnUrl("http://localhost:8765/pay/alipay/returnHandler");
//		//业务参数
//		request.setBizContent(data.toJSONString());
//		AlipayTradePagePayResponse alipayResponse=client.pageExecute(request);
//		response.setContentType("text/html;charset=UTF-8");
//		try {
//			response.getWriter().write(alipayResponse.getBody());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

	}

}
