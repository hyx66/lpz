package com.acooly.epei.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.OfflineSalesAcctStaService;
import com.acooly.epei.dao.OfflineSalesAcctStaDao;
import com.acooly.epei.domain.OfflineSalesAcctSta;
import com.acooly.module.security.domain.User;

@Service("offlineSalesActStaService")
public class OfflineSalesAcctStaServiceImpl extends EntityServiceImpl<OfflineSalesAcctSta, OfflineSalesAcctStaDao> implements OfflineSalesAcctStaService {

	@Override
	public void offlineSalesAcctStaConfirm(OfflineSalesAcctSta osas) {
		BigDecimal amount = osas.getAmount();
		String acctMemo = osas.getAcctMemo();
		
		osas = get(osas.getId());
		osas.setAmount(amount);
		osas.setAcctMemo(acctMemo);
		osas.setStatus(2);
		osas.setAcctTime(new Date());
		//管理员信息
		User loginUser= (User) SecurityUtils.getSubject().getPrincipal();
		//对账人员ID
		osas.setAcctId(loginUser.getId());
		//对账人员
		osas.setAcctName(loginUser.getRealName());
		update(osas);
	}

	@Override
	public List<OfflineSalesAcctSta> queryByHospitalName(String hospitalName) {
		Map<String, Object> params = new HashMap<>();
		params.put("EQ_hospitalName", hospitalName);
		List<OfflineSalesAcctSta> offlineSalesAcctSta = query(params, null);
		return offlineSalesAcctSta;
	}

}
