package com.acooly.epei.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 医院 Entity
 *
 * @author Acooly Code Generator
 * Date: 2015-10-19 18:47:37
 */
@Entity
@Table(name = "EP_HOSPITAL")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Hospital extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 医院名 */
	private String name;
	/** 医院logo */
	private Long logoImageId;
	/** 接待地点 */
	private String receptionPosition;
	/** 删除 */
	private int deleted;
	/**陪诊负责人编号*/
	private Long pzCusNo;
	/**陪诊负责人姓名*/
	private String pzPrincipalName;
	/**陪诊负责人电话*/
	private String pzPrincipalMobile;
	/**陪护负责人编号*/
	private Long phCusNo;
	/**陪护负责人姓名*/
	private String phPrincipalName;
	/**陪护负责人电话*/
	private String phPrincipalMobile;
	/**新加字段*/
	private Image hospitalLogo;
	/** 服务价格  */
	private List<ServicePrice> prices = new ArrayList<>();

	private List<ServicePrice> phPrices;

	private List<ServicePrice> pzPrices;
	/** 医院支持的服务类型 */
	private String serviceType;

	/** json格式的陪护价格表 */
	private String phPricesJson;

	/**json格式的陪诊价格表*/
	private String pzPricesJson;
	
	/**记录原陪诊管理员编号  方便修改  非数据库字段 */
	private Long pzCusNoOld;
	
	/**记录原陪护管理员编号  方便修改  非数据库字段 */
	private Long phCusNoOld;
	
	/**是否合作（合作医院费用下单不直接计算，非合作医费用格下单时直接计算）    0:合作     1：非合作*/
	private Integer isCooperate;
	
	/**陪护服务价格*/
	private BigDecimal phServicePrice;
	
	@Transient
	public Long getPzCusNoOld() {
		return pzCusNoOld;
	}
	public void setPzCusNoOld(Long pzCusNoOld) {
		this.pzCusNoOld = pzCusNoOld;
	}
	
	@Transient
	public Long getPhCusNoOld() {
		return phCusNoOld;
	}
	public void setPhCusNoOld(Long phCusNoOld) {
		this.phCusNoOld = phCusNoOld;
	}
	/***/

	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_EP_HOSPITAL") })
	public Long getId(){
		return this.id;
	}

	public void setId(Long id){
		this.id = id;
	}
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public Long getLogoImageId() {
		return logoImageId;
	}

	public void setLogoImageId(Long logoImageId) {
		this.logoImageId = logoImageId;
	}

	public String getReceptionPosition(){
		return this.receptionPosition;
	}
	
	public void setReceptionPosition(String receptionPosition){
		this.receptionPosition = receptionPosition;
	}

	public int getDeleted(){
		return this.deleted;
	}
	
	public void setDeleted(int deleted){
		this.deleted = deleted;
	}

	public Long getPzCusNo() {
		return pzCusNo;
	}
	public void setPzCusNo(Long pzCusNo) {
		this.pzCusNo = pzCusNo;
	}
	
	public String getPzPrincipalName() {
		return pzPrincipalName;
	}
	public void setPzPrincipalName(String pzPrincipalName) {
		this.pzPrincipalName = pzPrincipalName;
	}

	public String getPzPrincipalMobile() {
		return pzPrincipalMobile;
	}
	public void setPzPrincipalMobile(String pzPrincipalMobile) {
		this.pzPrincipalMobile = pzPrincipalMobile;
	}
	
	public Long getPhCusNo() {
		return phCusNo;
	}
	public void setPhCusNo(Long phCusNo) {
		this.phCusNo = phCusNo;
	}

	public String getPhPrincipalName() {
		return phPrincipalName;
	}
	public void setPhPrincipalName(String phPrincipalName) {
		this.phPrincipalName = phPrincipalName;
	}

	public String getPhPrincipalMobile() {
		return phPrincipalMobile;
	}
	public void setPhPrincipalMobile(String phPrincipalMobile) {
		this.phPrincipalMobile = phPrincipalMobile;
	}
	
	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="hospital_id")
	public List<ServicePrice> getPrices() {
		return prices;
	}

	public void setPrices(List<ServicePrice> prices) {
		this.prices = prices;
	}

	@Transient
	public List<ServicePrice> getPhPrices() {
		return phPrices;
	}

	public void setPhPrices(List<ServicePrice> phPrices) {
		this.phPrices = phPrices;
	}

	@Transient
	public List<ServicePrice> getPzPrices() {
		return pzPrices;
	}

	public void setPzPrices(List<ServicePrice> pzPrices) {
		this.pzPrices = pzPrices;
	}

	@Transient
	public Image getHospitalLogo() {
		return hospitalLogo;
	}

	public void setHospitalLogo(Image hospitalLogo) {
		this.hospitalLogo = hospitalLogo;
	}

	@Transient
	public String getPhPricesJson() {
		return phPricesJson;
	}

	public void setPhPricesJson(String phPricesJson) {
		this.phPricesJson = phPricesJson;
	}

	@Transient
	public String getPzPricesJson() {
		return pzPricesJson;
	}

	public void setPzPricesJson(String pzPricesJson) {
		this.pzPricesJson = pzPricesJson;
	}

	public void classifyPrice(){
		pzPrices = new ArrayList<>();
		phPrices = new ArrayList<>();
		for(ServicePrice price : prices){
			if(ServiceTypeEnum.PH.code().equals(price.getServiceType())){
				phPrices.add(price);
			}
			else{
				pzPrices.add(price);
			}
		}
	}

	public Integer getIsCooperate() {
		return isCooperate;
	}
	
	public void setIsCooperate(Integer isCooperate) {
		this.isCooperate = isCooperate;
	}
	
	public BigDecimal getPhServicePrice() {
		return phServicePrice;
	}
	
	public void setPhServicePrice(BigDecimal phServicePrice) {
		this.phServicePrice = phServicePrice;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
