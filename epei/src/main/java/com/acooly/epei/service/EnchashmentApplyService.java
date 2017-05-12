package com.acooly.epei.service;

import java.util.List;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.EnchashmentApply;

/**
 * 申请提现 Service
 */
public interface EnchashmentApplyService extends EntityService<EnchashmentApply> {

	void transfer(EnchashmentApply e);

	List<EnchashmentApply> queryByCustomerId(Long id);
}
