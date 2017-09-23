package com.easybusiness.attendance.processor;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.easybusiness.attendancepersistence.deviceattendance.DeviceAttendanceDao;
import com.easybusiness.attendancepersistence.entity.DeviceAttendance;
import com.easybusiness.attendancepersistence.entity.UserDeviceEffort;
import com.easybusiness.attendancepersistence.user.UserDao;

@Component
public class AttendanceDataTransactionProcessor implements Processor {
	private static final Logger LOGGER = LoggerFactory.getLogger(AttendanceDataTransactionProcessor.class);
	
	@Autowired
	DeviceAttendanceDao deviceAttendanceDao;
	
	@Autowired
	UserDao userDao;

	private DeviceAttendance deviceAttendance;

	@Override
	public void process(Exchange exchange) throws Exception {
	    try{
		LOGGER.info("in AttendanceDataTransactionProcessor ");
		List<UserDeviceEffort> deviceAttendanceList=deviceAttendanceDao.groupByUserAndDate(new java.sql.Date((new SimpleDateFormat("dd-MM-yyyy").parse("13-08-2017")).getTime()));
	   LOGGER.info("size of transaction data is "+deviceAttendanceList.size());
	    }
	    catch(Exception e)
	    {
		LOGGER.error("exception while transaction  data  is {}",e.getMessage());
		throw e;
	    }

	}

}
