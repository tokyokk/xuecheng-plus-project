package com.xuecheng.orders.api;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.orders.config.AlipayConfig;
import com.xuecheng.orders.model.dto.AddOrderDto;
import com.xuecheng.orders.model.dto.PayRecordDto;
import com.xuecheng.orders.model.dto.PayStatusDto;
import com.xuecheng.orders.model.po.XcPayRecord;
import com.xuecheng.orders.service.OrderService;
import com.xuecheng.orders.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.xuecheng.orders.config.AlipayConfig.CHARSET;

/**
 * @author Mr.Z
 * @description 订单服务的相关接口
 * @create 2023-09-07 14:38
 * @github https://github.com/Ragnarokoo
 */
@Slf4j
@Api(value = "订单支付接口", tags = "订单支付接口")
@Controller
public class OrderController
{
    @Autowired
    private OrderService orderService;

    @Value("${pay.alipay.APP_ID}")
    private String APP_ID;

    @Value("${pay.alipay.PRIVATE_KEY}")
    private String APP_PRIVATE_KEY;

    @Value("${pay.alipay.ALIPAY_PUBLIC_KEY}")
    private String ALIPAY_PUBLIC_KEY;

    @ApiOperation("生成支付二维码")
    @PostMapping("generatepaycode")
    @ResponseBody
    public PayRecordDto generatePayCode(@RequestBody AddOrderDto addOrderDto)
    {
        SecurityUtil.XcUser user = SecurityUtil.getUser();
        String userId = user.getId();
        // 调用service,完成插入订单信息,插入支付记录,生成支付二维码
        PayRecordDto payRecordDto = orderService.createOrder(userId, addOrderDto);

        return payRecordDto;
    }

    @ApiOperation("扫码下单接口")
    @GetMapping("/requestpay")
    public void requestpay(String payNo, HttpServletResponse httpResponse) throws IOException, AlipayApiException
    {
        // 传入支付记录号, 判断支付记录号是否存在
        XcPayRecord payRecordByPayNo = orderService.getPayRecordByPayNo(payNo);
        if (payRecordByPayNo == null)
        {
            XueChengPlusException.cast("支付记录不存在");
        }
        // 支付结果
        String status = payRecordByPayNo.getStatus();
        if ("601002".equals(status))
        {
            XueChengPlusException.cast("已支付, 无需重复支付");
        }

        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, APP_ID, APP_PRIVATE_KEY, AlipayConfig.FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
        //获得初始化的AlipayClient
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
        alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
        alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                " \"out_trade_no\":\"" + payNo + "\"," +
                " \"total_amount\":" + payRecordByPayNo.getTotalPrice() + "," +
                " \"subject\":\"" + payRecordByPayNo.getOrderName() + "\"," +
                " \"product_code\":\"QUICK_WAP_WAY\"" +
                " }");//填充业务参数
        String form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK请求支付宝,开始下单
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
    }

    @ApiOperation("查询支付结果")
    @GetMapping("/payresult")
    @ResponseBody
    public PayRecordDto payresult(String payNo) throws IOException
    {
        // 查询支付结果
        PayRecordDto payRecordDto = orderService.queryPayResult(payNo);
        return payRecordDto;
    }

    /**
     * 接收通知
     * @param request
     * @param response
     */
    @PostMapping("/paynotify")
    public void paynotidy(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException
    {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); )
        {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++)
            {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean verify_result = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, "RSA2");

        if (verify_result)
        {//验证成功
            // ////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
            //状态
            String total_amount = new String(request.getParameter("trade_amount").getBytes("ISO-8859-1"), "UTF-8");

            //------请根据您的业务逻辑来编写程序（以下代码仅作参考）------

            if ("TRADE_SUCCESS".equals(trade_status))
            {
                // 更新支付表的支付状态为成功,订单表的状态为成功
                PayStatusDto payStatusDto = new PayStatusDto();
                payStatusDto.setOut_trade_no(out_trade_no);
                payStatusDto.setTrade_no(trade_no);
                payStatusDto.setTrade_status(trade_status);
                payStatusDto.setApp_id(APP_ID);
                payStatusDto.setTotal_amount(total_amount);

                orderService.saveAliPayStatus(payStatusDto);
            }
                response.getWriter().write("success");
            } else
            { // 验证失败
                response.getWriter().write("fail");
            }

    }

}