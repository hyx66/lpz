package com.acooly.epei.timer;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acooly.epei.domain.Department;
import com.acooly.epei.domain.Hospital;
import com.acooly.epei.domain.OfflineSalesAcctSta;
import com.acooly.epei.service.DepartmentService;
import com.acooly.epei.service.HospitalService;
import com.acooly.epei.service.OfflineSalesAcctStaService;
import com.acooly.epei.service.OfflineSalesRecordsService;


/**
 * 线下消费记录按账期与有效状态入账统计的定时任务
 * @author Administrator
 */
@Component("oSASEndOfDayStatistics")
public class OSASEndOfDayStatistics {

	private static Logger logger  = LoggerFactory.getLogger(OSASEndOfDayStatistics.class);
	
	@Autowired
	private OfflineSalesRecordsService offlineSalesRecordsService;
	
	@Autowired
	private OfflineSalesAcctStaService offlineSalesAcctStaService;
	
	@Autowired
	HospitalService hospitalService;
	
	@Autowired
	DepartmentService departmentService;
	
	public void main(String[] args){
		logger.info("线下消费记录按账期与有效状态入账统计的定时任务开始");
		Date dNow = new Date();						//当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);						//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);	//设置为前一天
		dBefore = calendar.getTime();				//得到前一天的时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");	//设置时间格式
		String acctYmd = sdf.format(dBefore);						//格式化前一天
		
		//按照不同医院的不同科室进行统计
		for(Department d : departmentService.getAll()){
		//陪诊
		BigDecimal totalOfflineAmountPh = offlineSalesRecordsService.sumAmountByDataymdAndDepartmentIdAndDataTypeAndStatus(acctYmd, d.getId(), 1, 1);
		if(totalOfflineAmountPh!=null){
			OfflineSalesAcctSta offlineSalesAcctSta = new OfflineSalesAcctSta();
			offlineSalesAcctSta.setDataCount(offlineSalesRecordsService.countAmountByDataymdAndDepartmentIdAndDataTypeAndStatus(acctYmd, d.getId(), 1, 1));
			Hospital h = hospitalService.get(d.getHospitalId());
			if(h!=null){
				offlineSalesAcctSta.setHospitalName(h.getName());
			}
			offlineSalesAcctSta.setHospitalId(d.getHospitalId());
			offlineSalesAcctSta.setDepartmentName(d.getName());
			offlineSalesAcctSta.setDepartmentId(d.getId());
			offlineSalesAcctSta.setAcctYmd(acctYmd);
			offlineSalesAcctSta.setOfflineAmount(totalOfflineAmountPh);
			offlineSalesAcctSta.setCreateId((long)0);
			offlineSalesAcctSta.setCreateName("系统日终统计");
			offlineSalesAcctSta.setStatus(0);
			offlineSalesAcctSta.setCountScope(3);
			offlineSalesAcctSta.setOrderType(2);
			offlineSalesAcctStaService.save(offlineSalesAcctSta);
		}
			//陪护
			BigDecimal totalOfflineAmountPz = offlineSalesRecordsService.sumAmountByDataymdAndDepartmentIdAndDataTypeAndStatus(acctYmd, d.getId(), 0, 1);
			if(totalOfflineAmountPz!=null){
				OfflineSalesAcctSta offlineSalesAcctSta = new OfflineSalesAcctSta();
				offlineSalesAcctSta.setDataCount(offlineSalesRecordsService.countAmountByDataymdAndDepartmentIdAndDataTypeAndStatus(acctYmd, d.getId(), 0, 1));
				Hospital h = hospitalService.get(d.getHospitalId());
				if(h!=null){
					offlineSalesAcctSta.setHospitalName(h.getName());
				}
				offlineSalesAcctSta.setHospitalId(d.getHospitalId());
				offlineSalesAcctSta.setDepartmentName(d.getName());
				offlineSalesAcctSta.setDepartmentId(d.getId());
				offlineSalesAcctSta.setAcctYmd(acctYmd);
				offlineSalesAcctSta.setOfflineAmount(totalOfflineAmountPz);
				offlineSalesAcctSta.setCreateId((long)0);
				offlineSalesAcctSta.setCreateName("系统日终统计");
				offlineSalesAcctSta.setStatus(0);
				offlineSalesAcctSta.setCountScope(3);
				offlineSalesAcctSta.setOrderType(1);
				offlineSalesAcctStaService.save(offlineSalesAcctSta);
			}
		}
		
		//按照不同的医院进行统计
		for(Hospital h : hospitalService.getAll()){
			BigDecimal totalOfflineAmount = offlineSalesRecordsService.sumAmountByDataymdAndStatusAndHospitalId(acctYmd, 1, h.getId());
			if(totalOfflineAmount!=null){
				OfflineSalesAcctSta offlineSalesAcctSta = new OfflineSalesAcctSta();
				offlineSalesAcctSta.setDataCount(offlineSalesRecordsService.countAmountByDataymdAndStatusAndHospitalId(acctYmd, 1, h.getId()));
				offlineSalesAcctSta.setHospitalName(h.getName());
				offlineSalesAcctSta.setHospitalId(h.getId());
				offlineSalesAcctSta.setAcctYmd(acctYmd);
				offlineSalesAcctSta.setOfflineAmount(totalOfflineAmount);
				offlineSalesAcctSta.setCreateId((long)0);
				offlineSalesAcctSta.setCreateName("系统日终统计");
				offlineSalesAcctSta.setStatus(0);
				offlineSalesAcctSta.setCountScope(2);
				offlineSalesAcctSta.setOrderType(3);
				offlineSalesAcctStaService.save(offlineSalesAcctSta);
			}	
		}
		
		//统计整个系统一天的金额总数
		BigDecimal offlineAmount = offlineSalesRecordsService.sumAmountByDataymdAndStatus(acctYmd,1);
		if(offlineAmount!=null){
			OfflineSalesAcctSta offlineSalesAcctSta = new OfflineSalesAcctSta();
			offlineSalesAcctSta.setDataCount(offlineSalesRecordsService.countAmountByDataymdAndStatus(acctYmd, 1));
			offlineSalesAcctSta.setAcctYmd(acctYmd);
			offlineSalesAcctSta.setOfflineAmount(offlineAmount);
			offlineSalesAcctSta.setCreateId((long)0);
			offlineSalesAcctSta.setCreateName("系统日终统计");
			offlineSalesAcctSta.setStatus(0);
			offlineSalesAcctSta.setCountScope(1);
			offlineSalesAcctSta.setOrderType(3);
			offlineSalesAcctStaService.save(offlineSalesAcctSta);
		}
				
		logger.info("线下消费记录按账期与有效状态入账统计的定时任务结束");
		
	}
	
}
