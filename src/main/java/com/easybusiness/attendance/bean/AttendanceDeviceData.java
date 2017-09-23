package com.easybusiness.attendance.bean;

import java.io.Serializable;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator="\\~")
public class AttendanceDeviceData implements Serializable {

	private static final long serialVersionUID = 2988497078668321562L;
	
	@DataField(pos=1,trim=true)
	private String userDeviceId;
	
	@DataField(pos=2,trim=true)
	private String attendanceDate;
	
	@DataField(pos=3,trim=true)
	private String inTime;
	
	@DataField(pos=4,trim=true)
	private String outTime;
	

	public AttendanceDeviceData() {
		super();
	}


	public String getAttendanceDate() {
	    return attendanceDate;
	}

	public void setAttendanceDate(String attendanceDate) {
	    this.attendanceDate = attendanceDate;
	}

	public String getInTime() {
	    return inTime;
	}

	public void setInTime(String inTime) {
	    this.inTime = inTime;
	}

	public String getOutTime() {
	    return outTime;
	}

	public void setOutTime(String outTime) {
	    this.outTime = outTime;
	}


	public String getUserDeviceId() {
	    return userDeviceId;
	}


	public void setUserDeviceId(String userDeviceId) {
	    this.userDeviceId = userDeviceId;
	}


	@Override
	public String toString() {
	    return "AttendanceDeviceData [userDeviceId=" + userDeviceId + ", attendanceDate=" + attendanceDate
		    + ", inTime=" + inTime + ", outTime=" + outTime + "]";
	}



	
	

}
