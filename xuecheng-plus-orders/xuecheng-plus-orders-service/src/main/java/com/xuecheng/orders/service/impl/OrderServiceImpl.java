package com.xuecheng.orders.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.utils.IdWorkerUtils;
import com.xuecheng.base.utils.QRCodeUtil;
import com.xuecheng.messagesdk.model.po.MqMessage;
import com.xuecheng.messagesdk.service.MqMessageService;
import com.xuecheng.orders.config.AlipayConfig;
import com.xuecheng.orders.config.PayNotifyConfig;
import com.xuecheng.orders.mapper.XcOrdersGoodsMapper;
import com.xuecheng.orders.mapper.XcOrdersMapper;
import com.xuecheng.orders.mapper.XcPayRecordMapper;
import com.xuecheng.orders.model.dto.AddOrderDto;
import com.xuecheng.orders.model.dto.PayRecordDto;
import com.xuecheng.orders.model.dto.PayStatusDto;
import com.xuecheng.orders.model.po.XcOrders;
import com.xuecheng.orders.model.po.XcOrdersGoods;
import com.xuecheng.orders.model.po.XcPayRecord;
import com.xuecheng.orders.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Z
 * @description 订单相关的接口实现
 * @create 2023-09-07 14:44
 * @github https://github.com/Ragnarokoo
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService
{
    @Autowired
    private XcOrdersMapper ordersMapper;

    @Autowired
    private XcOrdersGoodsMapper ordersGoodsMapper;

    @Autowired
    private XcPayRecordMapper payRecordMapper;

    @Autowired
    private OrderServiceImpl currentProxy;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MqMessageService mqMessageService;

    @Value("${pay.qecodeurl}")
    private String qrcodeurl;

    @Value("${pay.alipay.APP_ID}")
    private String APP_ID;

    @Value("${pay.alipay.PRIVATE_KEY}")
    private String APP_PRIVATE_KEY;

    @Value("${pay.alipay.ALIPAY_PUBLIC_KEY}")
    private String ALIPAY_PUBLIC_KEY;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PayRecordDto createOrder(String userId, AddOrderDto addOrderDto)
    {
        // 插入订单表, 订单主表, 订单明细表
        XcOrders xcOrders = saveXcOrders(userId, addOrderDto);

        // 插入支付记录
        XcPayRecord payRecord = createPayRecord(xcOrders);
        Long payNo = payRecord.getPayNo();

        // 生成二维码
        QRCodeUtil qrCodeUtil = new QRCodeUtil();
        // 支付二维码的url
        String url = String.format(qrcodeurl, payNo);
        // 二维码图片
        String qrCode = null;
        try
        {
            qrCode = qrCodeUtil.createQRCode(url, 200, 200);
        } catch (IOException e)
        {
            XueChengPlusException.cast("生成二维码出错");
        }
        PayRecordDto payRecordDto = new PayRecordDto();
        BeanUtils.copyProperties(payRecord, payRecordDto);
        payRecordDto.setQrcode(qrCode);

        return payRecordDto;
    }

    @Override
    public XcPayRecord getPayRecordByPayNo(String payNo)
    {
        return payRecordMapper.selectOne(new LambdaQueryWrapper<XcPayRecord>()
                .eq(XcPayRecord::getPayNo, payNo));
    }

    @Override
    public PayRecordDto queryPayResult(String payNo)
    {
        // 调用支付宝的接口查询支付结果
        PayStatusDto payStatusDto = queryPayResultFromAliPay(payNo);

        // 拿到支付宝结果更新支付记录表和订单表的支付状态
        currentProxy.saveAliPayStatus(payStatusDto);
        // 要返回最新的支付记录信息
        XcPayRecord payRecordByPayNo = getPayRecordByPayNo(payNo);
        PayRecordDto payRecordDto = new PayRecordDto();
        BeanUtils.copyProperties(payRecordByPayNo, payRecordDto);

        return payRecordDto;
    }

    /**
     * @param payNo 支付交易号
     * @return com.xuecheng.orders.model.dto.PayStatusDto 支付结果信息
     * @description 保存支付宝查询结果
     * @author Mr.Z
     * @date 2023/9/7 19:01
     */
    public PayStatusDto queryPayResultFromAliPay(String payNo)
    {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, APP_ID, APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE); //获得初始化的AlipayClient
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", payNo);
        //bizContent.put("trade_no", "2014112611001004680073956707");
        request.setBizContent(bizContent.toString());
        AlipayTradeQueryResponse response = null;
        // 支付宝返回的信息
        String body = null;
        try
        {
            response = alipayClient.execute(request);
            body = response.getBody();
        } catch (AlipayApiException e)
        {
            XueChengPlusException.cast("请求支付宝查询支付结果异常");
        }
        if (response.isSuccess())
        {
            System.out.println("调用成功");
            System.out.println(response.getBody());
        } else
        {
            XueChengPlusException.cast("请求支付宝查询结果失败");
            System.out.println("调用失败");
        }

        Map<String, String> bodyMap = JSON.parseObject(body, Map.class);

        // 解析支付结果
        PayStatusDto payStatusDto = new PayStatusDto();
        payStatusDto.setOut_trade_no(payNo);
        payStatusDto.setTrade_no(bodyMap.get("trade_no")); // 支付宝的交易号
        payStatusDto.setTrade_status(bodyMap.get("trade_status")); // 交易状态
        payStatusDto.setApp_id(APP_ID);
        payStatusDto.setTotal_amount(bodyMap.get("total_amount")); // 总金额

        return payStatusDto;
    }

    /**
     * @param payStatusDto 支付结果信息 从支付宝查询到的信息
     * @description 保存支付宝支付结果
     * @author Mr.Z
     * @date 2023/9/7 19:02
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAliPayStatus(PayStatusDto payStatusDto)
    {
        // 支付记录号
        String payNo = payStatusDto.getOut_trade_no();
        XcPayRecord payRecordByPayNo = getPayRecordByPayNo(payNo);
        if (payRecordByPayNo == null)
        {
            XueChengPlusException.cast("找不到相关的支付记录");
        }
        // 拿到相关订单的id
        Long orderId = payRecordByPayNo.getOrderId();
        XcOrders xcOrders = ordersMapper.selectById(orderId);
        if (xcOrders == null)
        {
            XueChengPlusException.cast("找不到相关联的订单");
        }
        // 支付记录
        String statusFromDb = payRecordByPayNo.getStatus();
        // 如果数据库支付的状态已经是成功了, 那么就不在处理了
        if ("601002".equals(statusFromDb))
        {
            return;
        }

        // 如果支付成功
        String trade_status = payStatusDto.getTrade_status(); //从支付宝查询到的信息
        if ("TRADE_SUCCESS".equals(trade_status))
        { // 从支付宝返回的信息为支付成功
            // 更新支付记录表的状态为支付成功
            payRecordByPayNo.setStatus("601002");
            // 支付宝的订单号
            payRecordByPayNo.setOutPayNo(payStatusDto.getTrade_no());
            // 第三方支付渠道编号
            payRecordByPayNo.setOutPayChannel("Alipay");
            // 支付成功时间
            payRecordByPayNo.setPaySuccessTime(LocalDateTime.now());
            payRecordMapper.updateById(payRecordByPayNo);

            // 更新订单表的状态为支付成功
            xcOrders.setStatus("600002"); // 订单状态为交易成功
            ordersMapper.updateById(xcOrders);

            //  将消息写到数据库
            MqMessage mqMessage = mqMessageService.addMessage("payresult_notify", xcOrders.getOutBusinessId(), xcOrders.getOrderType(), null);
            // 发送消息
            notifyPayResult(mqMessage);
        }

    }

    @Override
    public void notifyPayResult(MqMessage mqMessage)
    {
        // 消息内容
        String jsonString = JSON.toJSONString(mqMessage);
        // 创建一个持久化消息
        Message messageObj = MessageBuilder.withBody(jsonString.getBytes(StandardCharsets.UTF_8)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();

        // 全局消息id
        Long id = mqMessage.getId();
        CorrelationData correlationData = new CorrelationData(id.toString());
        // 使用correlationData指定回调方法
        correlationData.getFuture().addCallback(result-> {
            if (result.isAck()) {
                // 消息发送到了交换机
                log.debug("发送消息成功:{}", jsonString);
                // 将消息从记录表mq_message中删掉
                mqMessageService.completed(id);
            } else {
                // 消息发送失败
                log.debug("发送消息失败:{}", jsonString);
            }
        }, ex-> {
            // 发生异常了
            log.debug("发送消息异常:{}", jsonString);
        });
        // 发送消息
        rabbitTemplate.convertAndSend(PayNotifyConfig.PAYNOTIFY_EXCHANGE_FANOUT, "", messageObj, correlationData);
    }

    /**
     * @param orders
     * @return com.xuecheng.orders.model.po.XcPayRecord
     * @description 保存支付记录
     * @author Mr.Z
     * @date 2023/9/7 15:15
     */
    public XcPayRecord createPayRecord(XcOrders orders)
    {
        // 订单id
        Long orderId = orders.getId();
        XcOrders xcOrders = ordersMapper.selectById(orderId);
        // 如果此订单不存在不能添加支付记录
        if (xcOrders == null)
        {
            XueChengPlusException.cast("订单不存在");
        }
        // 订单状态
        String status = xcOrders.getStatus();
        // 如果此订单支付结果为成功, 不能添加支付记录, 避免重复支付
        if ("601002".equals(status))
        { // 支付成功
            XueChengPlusException.cast("此订单已支付");
        }

        // 添加支付记录
        XcPayRecord xcPayRecord = new XcPayRecord();
        xcPayRecord.setPayNo(IdWorkerUtils.getInstance().nextId());// 支付记录号, 将来要传给支付宝
        xcPayRecord.setOrderId(orderId);
        xcPayRecord.setOrderName(xcOrders.getOrderName());
        xcPayRecord.setTotalPrice(xcOrders.getTotalPrice());
        xcPayRecord.setCurrency("CNY");
        xcPayRecord.setCreateDate(LocalDateTime.now());
        xcPayRecord.setStatus("601001"); // 未支付
        xcPayRecord.setUserId(xcPayRecord.getUserId());

        int insert = payRecordMapper.insert(xcPayRecord);
        if (insert <= 0)
        {
            XueChengPlusException.cast("插入支付记录失败");
        }

        return xcPayRecord;
    }

    /**
     * @param userId      用户id
     * @param addOrderDto 订单信息
     * @return com.xuecheng.orders.model.po.XcOrders
     * @description 保存订单信息
     * @author Mr.Z
     * @date 2023/9/7 14:56
     */
    public XcOrders saveXcOrders(String userId, AddOrderDto addOrderDto)
    {
        // 插入订单表: 订单主表,订单明细表
        // 进行幂等性判断, 同一个选课记录只有一个订单
        XcOrders xcOrders = getOrderByBusinessId(addOrderDto.getOutBusinessId());
        if (xcOrders != null)
        {
            return xcOrders;
        }

        // 插入订单主表
        xcOrders = new XcOrders();
        // 使用雪花算法生成订单号
        xcOrders.setId(IdWorkerUtils.getInstance().nextId());
        xcOrders.setTotalPrice(addOrderDto.getTotalPrice());
        xcOrders.setCreateDate(LocalDateTime.now());
        xcOrders.setStatus("600001"); // 未支付
        xcOrders.setUserId(userId);
        xcOrders.setOrderType("60201"); // 订单类型
        xcOrders.setOrderName(addOrderDto.getOrderName());
        xcOrders.setOrderDescrip(addOrderDto.getOrderDescrip());
        xcOrders.setOrderDetail(addOrderDto.getOrderDetail());
        xcOrders.setOutBusinessId(addOrderDto.getOutBusinessId()); // 如果是选课这里记录选课表的id
        int insert = ordersMapper.insert(xcOrders);
        if (insert <= 0)
        {
            XueChengPlusException.cast("添加订单失败");
        }
        // 订单id
        Long ordersId = xcOrders.getId();

        // 插入订单明细表
        // 将前端传入的明细JSON串转成List
        String orderDetailJson = addOrderDto.getOrderDetail();
        List<XcOrdersGoods> xcOrdersGoods = JSON.parseArray(orderDetailJson, XcOrdersGoods.class);

        // 遍历xcOrdersGoods插入订单明细表
        xcOrdersGoods.forEach(goods -> {
            goods.setOrderId(ordersId);
            ordersGoodsMapper.insert(goods);
        });

        return xcOrders;
    }

    /**
     * 根据业务id查询订单,业务id就是选课记录表中的主键
     *
     * @param businessId 业务id
     * @return
     */
    public XcOrders getOrderByBusinessId(String businessId)
    {
        return ordersMapper.selectOne(new LambdaQueryWrapper<XcOrders>().eq(XcOrders::getOutBusinessId, businessId));
    }
}