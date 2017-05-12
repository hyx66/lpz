package com.acooly.epei.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.CustomerAcctRecords;
import com.acooly.epei.domain.OrderBase;

/**
 * 订单基础信息 Service
 *
 * Date: 2015-10-26 21:28:11
 *
 * @author Acooly Code Generator
 *
 */
public interface OrderBaseService extends EntityService<OrderBase> {

	/**
	 * 根据orderId获取订单类型
	 * @param orderId
	 * @return
	 */
	String getOrderType(Long orderId);

	/**
	 * 根据订单编号获取订单信息
	 * @param orderNo
	 * @return
	 */
	OrderBase getByOrderNo(String orderNo);
	
	/**根据会员*/
	List<OrderBase> getTheCustomerOrders(Long cusNo);

	/**
	 * 取消订单
	 * @param orderNo
	 */
	void cancelOrder(String orderNo,boolean back);

	/**
	 * 创建订单
	 * @param order
	 */
	void createOrder(OrderBase order);

	/**
	 * 创建订单
	 * @param order
	 */
	void updateOrder(OrderBase order);

	/**
	 * 通知预约时间在明天的所有客户
	 * @return
	 */
	void notifyPatients();
	
	void insertAndPay(RedirectAttributes redirectAttributes,String orderDetail,String orderNo, String rechargeAmount) throws Exception;
	
	/**计算两个日期之间的天数*/
	int calculateDays(Date start,Date end) throws ParseException;
	
	/**根据起始日期和天数，计算结束日期。因为业务需要，当days=1时，start和end相等*/
	Date calculateDate(Date start, int days);

	//日期格式转换:将日期转换为格式为yyyy-MM-dd的形式
	Date dateChangeFormat(Date date) throws ParseException;

	CustomerAcctRecords createRechargetRecords(OrderBase order);

	//处理订单微信支付成功回调结果
	void handlePaymentResult(String outNo);
	
}
