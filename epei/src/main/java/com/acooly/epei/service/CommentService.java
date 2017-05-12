package com.acooly.epei.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.Comment;


public interface CommentService extends EntityService<Comment>{

	Comment findCommentByCustomerUserName(String CustomerUserName);
	Comment findByOrderId(Long orderId);

}
