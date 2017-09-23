package com.easybusiness.attendance.processor;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.easybusiness.attendance.bean.AttendanceDeviceData;
import com.easybusiness.attendancepersistence.biometricintegration.BiometricIntegrationDao;
import com.easybusiness.attendancepersistence.entity.BiometricIntegration;
import com.easybusiness.attendancepersistence.entity.UserDeviceMap;
import com.easybusiness.attendancepersistence.user.UserDao;
import com.easybusiness.attendancepersistence.userdevicemap.UserDeviceMapDao;

@Component
public class AttendanceProcessor implements Processor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttendanceProcessor.class);

    @Autowired
    UserDao userDao;

    @Autowired
    UserDeviceMapDao userDeviceMapDao;

    @Autowired
    BiometricIntegrationDao biometricIntegrationDao;


    @Override
    public void process(Exchange exchange) throws Exception {

	AttendanceDeviceData attendanceData = exchange.getIn().getBody(AttendanceDeviceData.class);
	LOGGER.info("data read from attendance file is :" + attendanceData.toString());
	try {
	    if (!"UserDeviceId".equalsIgnoreCase(attendanceData.getUserDeviceId())) {
		UserDeviceMap userDeviceMap = userDeviceMapDao
			.findByDeviceId(Long.parseLong(attendanceData.getUserDeviceId()));

		BiometricIntegration biometricIntegration = new BiometricIntegration();
		//
		
		DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");

		String inputText = attendanceData.getAttendanceDate();
		Date date = inputFormat.parse(inputText);
		String outputText = outputFormat.format(date);
		LOGGER.info("formatted input date is {}",outputText);
		
		//
		/*SimpleDateFormat printFormat = new SimpleDateFormat("dd-MM-yyyy");
		String inputDate=printFormat.format(attendanceData.getAttendanceDate());*/
		biometricIntegration.setUserDeviceMap(userDeviceMap);
		String inTimeString = attendanceData.getInTime();
		String outTimeString = attendanceData.getOutTime();
		Timestamp inTime = Timestamp.valueOf(outputText+" "+inTimeString);
		Timestamp outTime = Timestamp.valueOf(outputText+" "+outTimeString);
		biometricIntegration.setInTime(inTime);
		biometricIntegration.setOutTime(outTime);
		biometricIntegration.setUserDeviceLocation(null);
		biometricIntegration.setInDate(new java.sql.Date(
			(new SimpleDateFormat("dd-MM-yyyy").parse(attendanceData.getAttendanceDate())).getTime()));

		biometricIntegrationDao.addBiometricIntegration(biometricIntegration);
		LOGGER.info("biometric integration data successfully inserted for {}", attendanceData.toString());
	    }
	} catch (Exception e) {
	    LOGGER.error("exception while inserting device data for user devic Id {} is {}",
		    attendanceData.getUserDeviceId(), e.getMessage());
	    throw e;
	}

    }

}
