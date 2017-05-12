package com.acooly.epei.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.OfflineSalesRecordsService;
import com.acooly.epei.dao.OfflineSalesRecordsDao;
import com.acooly.epei.domain.OfflineSalesRecords;

@Service("offlineSalesRecordsService")
public class OfflineSalesRecordsServiceImpl extends EntityServiceImpl<OfflineSalesRecords, OfflineSalesRecordsDao> implements OfflineSalesRecordsService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("rawtypes")
	@Override
	public String getOfflineSalesRecordsByCreateTime(String time) {
		String sql = "select records_no from OFFLINE_SALES_RECORDS " +
				" where create_time like to_date('" + time + "','yyyy/mm/dd') order by create_time desc ";
		List osrList = jdbcTemplate.queryForList(sql,String.class);
		if(osrList.size() == 0){
			return null;
		}else{
			System.out.println(osrList.get(0));
			return (String) osrList.get(0);
		}
	}

	@Override
	public void offlineSalesRecordsConfirm(OfflineSalesRecords osr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		osr = get(osr.getId());
		osr.setStatus(1);
		osr.setDataYmd(sdf.format(new Date()));
		update(osr);
	}

	@Override
	public void cancelOfflineSalesRecords(Long id) {
		OfflineSalesRecords osr = get(id);
		if(osr.getStatus()==0){
			osr.setStatus(2);
		}else if(osr.getStatus()==2){
			osr.setStatus(0);
		}
		update(osr);
	}

	@Override
	public BigDecimal sumAmountByDataymdAndStatus(String defaultStartDate, int status) {
		String sql = "select sum(amount) from offline_sales_records where data_ymd='"+defaultStartDate+"' and status=1 ";
		BigDecimal amount = jdbcTemplate.queryForObject(sql, BigDecimal.class);
		return amount;
	}
	
	@Override
	public BigDecimal sumAmountByDataymdAndStatusAndHospitalId(String defaultStartDate, int status, Long hospitalId) {
		String sql = "select sum(amount) from offline_sales_records where data_ymd='"+defaultStartDate+"' and status=1 and hospital_id="+hospitalId;
		BigDecimal amount = jdbcTemplate.queryForObject(sql, BigDecimal.class);
		return amount;
	}

	@Override
	public BigDecimal sumAmountByDataymdAndDepartmentIdAndDataTypeAndStatus(
			String dataYmd, Long departmentId, int dataType, int status) {
		String sql = "select sum(amount) from offline_sales_records where data_ymd='"+dataYmd+"' and data_type="+dataType+" and depart_id="+departmentId+" and status=1";
		BigDecimal amount = jdbcTemplate.queryForObject(sql, BigDecimal.class);
		return amount;
	}

	@Override
	public int countAmountByDataymdAndStatus(String defaultStartDate,
			int status) {
		String sql = "select count(id) as count from offline_sales_records where data_ymd='"+defaultStartDate+"' and status=1 ";
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		return count;
	}

	@Override
	public int countAmountByDataymdAndStatusAndHospitalId(
			String defaultStartDate, int status, Long hospitalId) {
		String sql = "select count(id) as count from offline_sales_records where data_ymd='"+defaultStartDate+"' and status=1 and hospital_id="+hospitalId;
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		return count;
	}

	@Override
	public int countAmountByDataymdAndDepartmentIdAndDataTypeAndStatus(
			String dataYmd, Long departmentId, int dataType, int status) {
		String sql = "select count(id) as count from offline_sales_records where data_ymd='"+dataYmd+"' and data_type="+dataType+" and depart_id="+departmentId+" and status=1";
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		return count;
	}

	public int calculateDays(Date start,Date end){
		return ((Long)((end.getTime()-start.getTime())/(24*60*60*1000))).intValue();
	}
	
	public int getAge(Date birthDay){ 
        //获取当前系统时间
        Calendar cal = Calendar.getInstance(); 
        //如果出生日期大于当前时间，则抛出异常
        if (cal.before(birthDay)) { 
            throw new IllegalArgumentException( 
                "出生日期不能大于当前时间"); 
        } 
        //取出系统当前时间的年、月、日部分
        int yearNow = cal.get(Calendar.YEAR); 
        int monthNow = cal.get(Calendar.MONTH); 
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); 
        //将日期设置为出生日期
        cal.setTime(birthDay); 
        //取出出生日期的年、月、日部分  
        int yearBirth = cal.get(Calendar.YEAR); 
        int monthBirth = cal.get(Calendar.MONTH); 
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH); 
        //当前年份与出生年份相减，初步计算年龄
        int age = yearNow - yearBirth; 
        //当前月份与出生日期的月份相比，如果月份小于出生月份，则年龄上减1，表示不满多少周岁
        if (monthNow <= monthBirth) { 
            //如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
            if (monthNow == monthBirth) { 
                if (dayOfMonthNow < dayOfMonthBirth) age--; 
            }else{ 
                age--; 
            } 
        } 
        return age; 
    }
	
}
