package com.acooly.epei.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.dao.CommentDao;
import com.acooly.epei.domain.Comment;
import com.acooly.epei.service.CommentService;

@Service("CommentService")
public class CommentServiceImpl extends EntityServiceImpl<Comment,CommentDao> implements CommentService{

	@Override
	public Comment findCommentByCustomerUserName(String CustomerUserName) {
		return getEntityDao().findByCustomerUserName(CustomerUserName);
	}

	@Override
	public Comment findByOrderId(Long orderId) {
		return getEntityDao().findByOrderId(orderId);
	}

}
