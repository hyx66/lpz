package com.acooly.epei.dao;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.Comment;


/**
 * 
 * @author xierui
 *
 */

public interface CommentDao extends EntityJpaDao<Comment, Long> {
	
	Comment findByOrderId(Long orderId);
	Comment findByCustomerUserName(String CustomerUserName);
	              

}
