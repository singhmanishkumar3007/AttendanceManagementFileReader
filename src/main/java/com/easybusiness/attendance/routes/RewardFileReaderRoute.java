package com.easybusiness.attendance.routes;

import java.util.concurrent.ExecutorService;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.easybusiness.attendance.bean.AttendanceDeviceData;
import com.easybusiness.attendance.processor.RewardsProcessor;

@Component
public class RewardFileReaderRoute extends RouteBuilder {

    @Value("${attendancemanagement.fileinput}")
    private String fyFileInput;
    @Value("${attendancemanagement.defaultparameters}")
    private String defaultParams;

    private DataFormat attendanceDataFormat = new BindyCsvDataFormat(AttendanceDeviceData.class);
    @Autowired
    private RewardsProcessor rewardsProcessor;

    @Autowired
    private ExecutorService fixedThreadPool;

    @Override
    public void configure() throws Exception {
	/*
	 * from("direct:firstRoute") .log("Camel body: ${body}");
	 */
	from(RouteConstant.REWARD_ROUTE_ENDPOINT).routeId(RouteConstant.REWARD_ROUTE_ID).noAutoStartup().process(rewardsProcessor)
		.log("processing done").end();
    }
}
