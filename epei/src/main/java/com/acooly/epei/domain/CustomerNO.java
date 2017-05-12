package com.acooly.epei.domain;

import com.acooly.core.common.domain.AbstractEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 会员 Entity
 *
 * @author Acooly Code Generator
 * Date: 2015-10-19 18:47:37
 */
@Entity
@Table(name = "EP_CUSTOMER_NO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerNO extends AbstractEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_CUS_NO") })
	public Long getId(){
		return this.id;
	}

	public void setId(Long id){
		this.id = id;
	}

}
