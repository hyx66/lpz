package com.acooly.epei.domain;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * LXY_ORDER_BASE Entity
 *
 * Date: 2017-04-13 13:51:24
 *
 * @author Acooly Code Generator
 */
@Entity
@Table(name = "LXY_ORDER_BASE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LxyOrderBase extends TimeEntity {
	/** 订单总价 */
	private BigDecimal total;
	/** 只在待发货列表下生效，用于判断微团购订单是否已成团。其中 1已成团，0 未成团，-1 无意义新增此字段 */
	private int groupStatus;
	/** 0表示全部订单，1表示作为供货商的微订单，2表示普通订单 */
	private int isWeiOrder;
	/** 订单ID */
	private String orderId;
	/** 买家备注 */
	private String buyerNote;
	/** 分销商店铺名称 */
	private String fshopName;
	/** 下单时间 */
	private Date time;
	/** 分销商手机号 */
	private String fphone;
	/** 卖家备注 */
	private String sellerNote;
	/** 分销商编号 */
	private String fsellerId;
	/** 订单状态说明 */
	private String status2;
	/** 收货人的街道 */
	private String selfAddress;
	/** 收货人所在市 */
	private String city;
	/** 买家ID */
	private String buyerId;
	/** 买家姓名 */
	private String name;
	/** 收货地址 */
	private String address;
	/** 收货人所在省 */
	private String province;
	/** 收货人所在区 */
	private String region;
	/** 收货人电话 */
	private String phone;
	/** 邮政编码 */
	private String post;
	/** 快递公司编号 */
	private String expressType;
	/** 订单状态编号    0：下单    1：未付款    2：已付款    3：已发货    7：退款   8：订单关闭 */
	private String status;
	/** 快递单号 */
	private String expressNo;
	/** 快递费用 */
	private BigDecimal expressFee;
	/** 宝宝姓名 */
	private String babyName;
	/** 宝宝性别 */
	private Integer babySex;
	/** 服务医院 */
	private String hospitalName;
	/** 服务医院ID */
	private Long hospitalId;
	/** 科室名称 */
	private String departmentName;
	/** 科室ID */
	private Long departmentId;
	/** 预约时间 */
	private Date orderTime;
	/** 陪诊员ID */
	private Long pzId;
	/** 指派陪诊员 */
	private String pzName;
	/** 陪诊员联系方式 */
	private String pzPhone;
	/** 备注 */
	private String remark;
	/** 家长姓名 */
	private String patriarchName;
	/** 家长电话 */
	private String patriarchPhone;
	/** 家长身份证 */
	private String patriarchIdCard;
	/** 家庭地址 */
	private String site;
	/** 预约状态      1：未预约          2：已预约 */
	private Integer orderStatus = 1;
	/**订单详情*/
	private List<LxyOrderItem> item;
	/** 主题 */
	private String theme;
	
	@Id	
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence", value = "SEQ_LXY_ORDER_BASE") })
	public Long getId(){
		return this.id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	public BigDecimal getTotal(){
		return this.total;
	}
	
	public void setTotal(BigDecimal total){
		this.total = total;
	}
	public int getGroupStatus(){
		return this.groupStatus;
	}
	
	public void setGroupStatus(int groupStatus){
		this.groupStatus = groupStatus;
	}
	
	public int getIsWeiOrder(){
		return this.isWeiOrder;
	}
	
	public void setIsWeiOrder(int isWeiOrder){
		this.isWeiOrder = isWeiOrder;
	}
	public String getOrderId(){
		return this.orderId;
	}
	
	public void setOrderId(String orderId){
		this.orderId = orderId;
	}
	public String getBuyerNote(){
		return this.buyerNote;
	}
	
	public void setBuyerNote(String buyerNote){
		this.buyerNote = buyerNote;
	}
	public String getFshopName(){
		return this.fshopName;
	}
	
	public void setFshopName(String fshopName){
		this.fshopName = fshopName;
	}
	public Date getTime(){
		return this.time;
	}
	
	public void setTime(Date time){
		this.time = time;
	}
	public String getFphone(){
		return this.fphone;
	}
	
	public void setFphone(String fphone){
		this.fphone = fphone;
	}
	public String getSellerNote(){
		return this.sellerNote;
	}
	
	public void setSellerNote(String sellerNote){
		this.sellerNote = sellerNote;
	}
	public String getFsellerId(){
		return this.fsellerId;
	}
	
	public void setFsellerId(String fsellerId){
		this.fsellerId = fsellerId;
	}
	public String getStatus2(){
		return this.status2;
	}
	
	public void setStatus2(String status2){
		this.status2 = status2;
	}
	public String getSelfAddress(){
		return this.selfAddress;
	}
	
	public void setSelfAddress(String selfAddress){
		this.selfAddress = selfAddress;
	}
	public String getCity(){
		return this.city;
	}
	
	public void setCity(String city){
		this.city = city;
	}
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public String getAddress(){
		return this.address;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	public String getProvince(){
		return this.province;
	}
	
	public void setProvince(String province){
		this.province = province;
	}
	public String getRegion(){
		return this.region;
	}
	
	public void setRegion(String region){
		this.region = region;
	}
	public String getPhone(){
		return this.phone;
	}
	
	public void setPhone(String phone){
		this.phone = phone;
	}
	public String getPost(){
		return this.post;
	}
	
	public void setPost(String post){
		this.post = post;
	}
	public String getExpressType(){
		return this.expressType;
	}
	
	public void setExpressType(String expressType){
		this.expressType = expressType;
	}
	public String getStatus(){
		return this.status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}

	public String getExpressNo(){
		return this.expressNo;
	}
	
	public void setExpressNo(String expressNo){
		this.expressNo = expressNo;
	}
	public BigDecimal getExpressFee(){
		return this.expressFee;
	}
	
	public void setExpressFee(BigDecimal expressFee){
		this.expressFee = expressFee;
	}
	
	public String getBabyName(){
		return this.babyName;
	}
	
	public void setBabyName(String babyName){
		this.babyName = babyName;
	}
	public Integer getBabySex(){
		return this.babySex;
	}
	
	public void setBabySex(Integer babySex){
		this.babySex = babySex;
	}
	public String getHospitalName(){
		return this.hospitalName;
	}
	
	public void setHospitalName(String hospitalName){
		this.hospitalName = hospitalName;
	}

	public Long getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}

	
	public Date getOrderTime(){
		return this.orderTime;
	}
	
	public void setOrderTime(Date orderTime){
		this.orderTime = orderTime;
	}

	public Long getPzId() {
		return pzId;
	}

	public void setPzId(Long pzId) {
		this.pzId = pzId;
	}

	public String getPzName(){
		return this.pzName;
	}
	
	public void setPzName(String pzName){
		this.pzName = pzName;
	}
	public String getPzPhone(){
		return this.pzPhone;
	}
	
	public void setPzPhone(String pzPhone){
		this.pzPhone = pzPhone;
	}
	public String getRemark(){
		return this.remark;
	}
	
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getPatriarchName(){
		return this.patriarchName;
	}
	
	public void setPatriarchName(String patriarchName){
		this.patriarchName = patriarchName;
	}
	public String getSite(){
		return this.site;
	}
	
	public void setSite(String site){
		this.site = site;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="LXY_ORDER_BASE_ID")
	public List<LxyOrderItem> getItem() {
		return item;
	}

	public void setItem(List<LxyOrderItem> item) {
		this.item = item;
	}

	public String getPatriarchPhone() {
		return patriarchPhone;
	}

	public void setPatriarchPhone(String patriarchPhone) {
		this.patriarchPhone = patriarchPhone;
	}

	public String getPatriarchIdCard() {
		return patriarchIdCard;
	}

	public void setPatriarchIdCard(String patriarchIdCard) {
		this.patriarchIdCard = patriarchIdCard;
	}

	@Transient
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
