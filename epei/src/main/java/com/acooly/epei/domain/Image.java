package com.acooly.epei.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.acooly.core.common.domain.AbstractEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * EPEI_IMAGE Entity
 *
 * @author Acooly Code Generator
 * Date: 2015-10-21 16:53:09
 */
@Entity
@Table(name = "EP_IMAGE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Image extends AbstractEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** ID */
	private Long id;
	/** 扩展名 */
	private String ext;
	/** 文件名 */
	private String fileName;
	/** 大小 */
	private Long fileSize;
	/** 内容 */
	private byte[] content;
	
	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_EP_IMAGE") })
	public Long getId(){
		return this.id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	public String getExt(){
		return this.ext;
	}
	
	public void setExt(String ext){
		this.ext = ext;
	}
	public String getFileName(){
		return this.fileName;
	}
	
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	public Long getFileSize(){
		return this.fileSize;
	}
	
	public void setFileSize(Long fileSize){
		this.fileSize = fileSize;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	@Override public String toString() {
		return "Image{id="+id+",fileName="+fileName+",ext="+ext+",fileSize="+fileSize+"}";
	}
}
