package com.easybusiness.attendance.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.easybusiness.attendance.processor.SchedulerProcessor;

@Component
public class SchedulerRoute extends RouteBuilder {

    @Autowired
    private SchedulerProcessor schedulerProcessor;

    @Value("${attendancemanagement.quartz.endpointuri}")
    private String endPointUri;

    @Value("${attendancemanagement.quartz.cronexpression}")
    private String cronExpression;

    public void configure() throws Exception {
	from(getSchdulerEndpoinURL()).routeId(RouteConstant.SCHEDULER_ROUTE_ID).log("Starting the File Reader")
		.process(schedulerProcessor).end();
    }

    public String getSchdulerEndpoinURL() {
	return endPointUri + "?" + cronExpression;
    }

}