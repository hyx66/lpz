package com.acooly.epei.service;

import java.math.BigDecimal;
import java.util.Date;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.OfflineSalesRecords;

public interface OfflineSalesRecordsService extends EntityService<OfflineSalesRecords> {

	String getOfflineSalesRecordsByCreateTime(String time);

	void offlineSalesRecordsConfirm(OfflineSalesRecords osr);

	void cancelOfflineSalesRecords(Long id);

	BigDecimal sumAmountByDataymdAndStatus(String defaultStartDate, int status);

	BigDecimal sumAmountByDataymdAndStatusAndHospitalId(
			String defaultStartDate, int status, Long hospitalId);

	BigDecimal sumAmountByDataymdAndDepartmentIdAndDataTypeAndStatus(String dataYmd, Long departmentId, int dataType, int status);

	int countAmountByDataymdAndStatus(String defaultStartDate, int status);

	int countAmountByDataymdAndStatusAndHospitalId(
			String defaultStartDate, int status, Long hospitalId);

	int countAmountByDataymdAndDepartmentIdAndDataTypeAndStatus(String dataYmd, Long departmentId, int dataType, int status);
	
	int calculateDays(Date start,Date end);
	
	int getAge(Date birthday);
}
