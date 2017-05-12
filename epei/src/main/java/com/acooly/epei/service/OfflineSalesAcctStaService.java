package com.acooly.epei.service;

import java.util.List;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.OfflineSalesAcctSta;

public interface OfflineSalesAcctStaService extends EntityService<OfflineSalesAcctSta> {

	void offlineSalesAcctStaConfirm(OfflineSalesAcctSta osas);
	
	List<OfflineSalesAcctSta> queryByHospitalName(String hospitalName);
}
