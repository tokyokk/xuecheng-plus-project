package com.xuecheng.orders.service;

import com.xuecheng.messagesdk.model.po.MqMessage;
import com.xuecheng.orders.model.dto.AddOrderDto;
import com.xuecheng.orders.model.dto.PayRecordDto;
import com.xuecheng.orders.model.dto.PayStatusDto;
import com.xuecheng.orders.model.po.XcPayRecord;

/**
 * @author Mr.Z
 * @description 订单相关的service接口
 * @create 2023-09-07 14:42
 * @github https://github.com/Ragnarokoo
 */
public interface OrderService
{
    /**
     * @description 创建商品订单
     * @author Mr.Z
     * @date 2023/9/7 14:43
     * @param userId 用户id
     * @param addOrderDto  支付记录(包括二维码)
     * @return com.xuecheng.orders.model.dto.PayRecordDto 
     */
    public PayRecordDto createOrder(String userId, AddOrderDto addOrderDto);

    /**
     * @description 查询支付记录
     * @author Mr.Z
     * @date 2023/9/7 18:40
     * @param payNo 交易记录号
     * @return com.xuecheng.orders.model.po.XcPayRecord
     */
    public XcPayRecord getPayRecordByPayNo(String payNo);

    /**
     * @description 请求支付宝查询支付结果
     * @author Mr.Z
     * @date 2023/9/7 18:58
     * @param payNo 支付记录id
     * @return com.xuecheng.orders.model.dto.PayRecordDto 支付记录信息
     */
    public PayRecordDto queryPayResult(String payNo);
    
    /**
     * @description `保存支付状态
     * @author Mr.Z
     * @date 2023/9/7 19:46
     * @param payStatusDto 
     * @return void 
     */
    public void saveAliPayStatus(PayStatusDto payStatusDto);

    /**
     * @description 发送通知结果
     * @author Mr.Z
     * @date 2023/9/7 20:15
     * @param mqMessage 
     * @return void 
     */
    public void notifyPayResult(MqMessage mqMessage);
}
