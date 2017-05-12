package com.acooly.epei.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 会员 Entity
 *
 * @author Acooly Code Generator
 * Date: 2015-10-19 18:47:37
 */
@Entity
@Table(name = "EP_CUSTOMER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties({"coupons"})
public class Customer extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 手机号 */
	private String mobile;
	/** 会员编号 */
	private Long cusNo;
	/** 姓名 */
	private String name;
	/** 账户    后台可以变更值，重要的信息必要用账户名关联*/
	private String userName;
	/** 密码 */
	private String password;

    private String salt;
    
	/** 注册来源 */
	private String regOrigin;
	
	/** 删除 */
	private int deleted;

	/**身份证号*/
	private String idCard;

	/**微信用户openid**/
	private String openid;

	/**用户类型	0：普通会员	1：医院管理员*/
	private String customerType;
	
	/**管理员类型	0：陪诊管理员	1：陪护管理员*/
	private Integer adminType;
	
	/**医院管理员所负责的医院编号	-1：待定安排*/
	private Long customerHosId;
	
	/**推荐人*/
	private String referenceMobile;
	/**新加字段*/

	/** 优惠券 */
	private List<Coupon> coupons = new ArrayList<>();
	
	/**性别                0:女              1：男*/
	private String sex;
	
	/**生日*/
	private String birthday;
	
	/**籍贯*/
	private String nativePlace;
	
	/**邮箱*/
	private String email;
	
	/**职业*/
	private String profession;
	
	/**地址*/
	private String address;
	
	/**家庭成员*/
	private String family;
	
	/**婚姻状况       0：未婚             1：已婚*/
	private String maritalStatus;
	
	/**文化程度*/
	private String degreeOfEducation;
	
	/**紧急联系人*/
	private String emergencyContactPerson;
	
	/**紧急联系人与会员的关系*/
	private String emergencyContactRelationship;
	
	/**紧急联系电话*/
	private String emergencyContactNumber;
	
	/**个人病史*/
	private String medicalHistory;
	
	/**遗传史*/
	private String geneticHistory;
	
	/**生活习惯*/
	private String habits;
	
	/**膳食结构*/
	private String diet;
	
	/**体检情况*/
	private String physical;
	
	/**会员来源                网络     朋友推荐      医院偶然发现         活动现场推广*/
	private String source;
	
	/**愿意那种方式接收会员特权活动信息        免费短信         电话         邮箱           QQ*/
	private String receiveInfo;
	
	/**手机号码（区别于之前的手机号码，与用户账号无关，可以随时修改，可以保存座机号码）*/
	private String phoneNumber;
	
	/**关联vip表中的id*/
	private Long vipId;
	
	/**vip级别*/
	private Integer vipGrade;
	
	/**购买vip会员的企业表中的id*/
	private Long vipCompanyId;
	
	/**成为vip的时间*/
	private Date vipTime;
	
	/**vip是否激活      1：未激活       2已激活*/
	private Integer vipActivated;
	
	/**录入vip会员的系统管理员id*/
	private Long vipCreateUserId;
	
	/**vip会员系统管理员账号*/
	private String vipCreateUserName;

	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_EP_CUSTOMER") })
	public Long getId(){
		return this.id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public String getMobile(){
		return this.mobile;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public Long getCusNo(){
		return this.cusNo;
	}
	
	public void setCusNo(Long cusNo){
		this.cusNo = cusNo;
	}
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public String getUserName(){
		return this.userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	public String getRegOrigin(){
		return this.regOrigin;
	}
	
	public void setRegOrigin(String regOrigin){
		this.regOrigin = regOrigin;
	}
	public int getDeleted(){
		return this.deleted;
	}
	
	public void setDeleted(int deleted){
		this.deleted = deleted;
	}

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

	@OneToMany(cascade=CascadeType.ALL,mappedBy = "customer")
	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	public Integer getAdminType() {
		return adminType;
	}
	public void setAdminType(Integer adminType) {
		this.adminType = adminType;
	}
	
	public Long getCustomerHosId() {
		return customerHosId;
	}
	public void setCustomerHosId(Long customerHosId) {
		this.customerHosId = customerHosId;
	}
	
	public String getReferenceMobile() {
		return referenceMobile;
	}
	public void setReferenceMobile(String referenceMobile) {
		this.referenceMobile = referenceMobile;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getDegreeOfEducation() {
		return degreeOfEducation;
	}

	public void setDegreeOfEducation(String degreeOfEducation) {
		this.degreeOfEducation = degreeOfEducation;
	}

	public String getEmergencyContactPerson() {
		return emergencyContactPerson;
	}

	public void setEmergencyContactPerson(String emergencyContactPerson) {
		this.emergencyContactPerson = emergencyContactPerson;
	}

	public String getEmergencyContactRelationship() {
		return emergencyContactRelationship;
	}

	public void setEmergencyContactRelationship(String emergencyContactRelationship) {
		this.emergencyContactRelationship = emergencyContactRelationship;
	}

	public String getEmergencyContactNumber() {
		return emergencyContactNumber;
	}

	public void setEmergencyContactNumber(String emergencyContactNumber) {
		this.emergencyContactNumber = emergencyContactNumber;
	}

	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public String getGeneticHistory() {
		return geneticHistory;
	}

	public void setGeneticHistory(String geneticHistory) {
		this.geneticHistory = geneticHistory;
	}

	public String getHabits() {
		return habits;
	}

	public void setHabits(String habits) {
		this.habits = habits;
	}

	public String getDiet() {
		return diet;
	}

	public void setDiet(String diet) {
		this.diet = diet;
	}

	public String getPhysical() {
		return physical;
	}

	public void setPhysical(String physical) {
		this.physical = physical;
	}
	

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getReceiveInfo() {
		return receiveInfo;
	}

	public void setReceiveInfo(String receiveInfo) {
		this.receiveInfo = receiveInfo;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public Long getVipId() {
		return vipId;
	}

	public void setVipId(Long vipId) {
		this.vipId = vipId;
	}

	public Integer getVipGrade() {
		return vipGrade;
	}

	public void setVipGrade(Integer vipGrade) {
		this.vipGrade = vipGrade;
	}

	public Long getVipCompanyId() {
		return vipCompanyId;
	}

	public void setVipCompanyId(Long vipCompanyId) {
		this.vipCompanyId = vipCompanyId;
	}

	public Date getVipTime() {
		return vipTime;
	}

	public void setVipTime(Date vipTime) {
		this.vipTime = vipTime;
	}

	public Integer getVipActivated() {
		return vipActivated;
	}

	public void setVipActivated(Integer vipActivated) {
		this.vipActivated = vipActivated;
	}

	public Long getVipCreateUserId() {
		return vipCreateUserId;
	}

	public void setVipCreateUserId(Long vipCreateUserId) {
		this.vipCreateUserId = vipCreateUserId;
	}

	public String getVipCreateUserName() {
		return vipCreateUserName;
	}

	public void setVipCreateUserName(String vipCreateUserName) {
		this.vipCreateUserName = vipCreateUserName;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
