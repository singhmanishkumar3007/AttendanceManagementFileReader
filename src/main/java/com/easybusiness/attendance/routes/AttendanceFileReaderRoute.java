package com.easybusiness.attendance.routes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.easybusiness.attendance.bean.AttendanceDeviceData;
import com.easybusiness.attendance.processor.AttendanceProcessor;

@Component
public class AttendanceFileReaderRoute extends RouteBuilder {

    @Value("${attendancemanagement.fileinput}")
    private String fyFileInput;
    @Value("${attendancemanagement.defaultparameters}")
    private String defaultParams;

    private DataFormat attendanceDataFormat = new BindyCsvDataFormat(AttendanceDeviceData.class);
    @Autowired
    private AttendanceProcessor attendanceProcessor;

    @Autowired
    private ExecutorService fixedThreadPool;

    @Override
    public void configure() throws Exception {
	/*
	 * from("direct:firstRoute") .log("Camel body: ${body}");
	 */
	from(fyFileInput + defaultParams).routeId(RouteConstant.ATTENDANCE_READER_ROUTE_ID).noAutoStartup().choice()
		.when(body().isNull()).process(new Processor() {
		    public void process(Exchange exchange) throws Exception {

			exchange.getContext().stopRoute(RouteConstant.ATTENDANCE_READER_ROUTE_ID, 10, TimeUnit.SECONDS);
			//exchange.getContext().stop();
		    }
		}).end().split(body().tokenize("\n")).streaming().executorService(fixedThreadPool)
		.unmarshal(attendanceDataFormat).process(attendanceProcessor).to(RouteConstant.REWARD_ROUTE_ENDPOINT)
		.log("processing done").end();
    }
}
