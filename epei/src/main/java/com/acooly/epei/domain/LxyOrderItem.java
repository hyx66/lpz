package com.acooly.epei.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.GenericGenerator;

/**
 * 陪诊详情 Entity
 *
 * @author Acooly Code Generator
 * Date: 2015-10-26 21:28:11
 */
@Entity
@Table(name = "LXY_ORDER_ITEM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LxyOrderItem extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 商品ID */
	private String itemId;
	/** 商品名称 */
	private String  itemName;
	/**商品编号*/
	private String merchantCode;
	/**商品地址*/
	private String url;
	/**商品图片*/
	private String img;
	/**商品单价*/
	private BigDecimal price;
	/**商品数量*/
	private Integer quantity;
	/**商品总价*/
	private BigDecimal totalPrice;
	/** 商品型号 */
	private String skuId;
	/** 商品型号名称 */
	private String skuTitle;

	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence",
			value = "SEQ_LXY_ORDER_ITEM") })
	public Long getId(){
		return this.id;
	}
	
	public void setId(Long id){
		this.id = id;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getSkuTitle() {
		return skuTitle;
	}

	public void setSkuTitle(String skuTitle) {
		this.skuTitle = skuTitle;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
