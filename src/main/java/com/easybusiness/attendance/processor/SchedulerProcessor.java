package com.easybusiness.attendance.processor;

import java.util.Random;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.easybusiness.attendance.routes.RouteConstant;

@Component
public class SchedulerProcessor implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

	LOGGER.debug("SchedulerProcessor Starting...");
	LOGGER.debug("SchedulerProcessor Starting In normal flow...");
	initializeRoutes(exchange.getContext());
	startRoutes(exchange.getContext());
	markJobAsStarted(exchange);

	LOGGER.debug("SchedulerProcessor Started...");

    }

    protected void initializeRoutes(CamelContext context) throws Exception {
	context.stopRoute(RouteConstant.REWARD_ROUTE_ID);
	context.stopRoute(RouteConstant.ATTENDANCE_READER_ROUTE_ID);
    }

    protected void startRoutes(CamelContext context) throws Exception {
	context.startRoute(RouteConstant.REWARD_ROUTE_ID);
	context.startRoute(RouteConstant.ATTENDANCE_READER_ROUTE_ID);
    }

    private void markJobAsStarted(Exchange exchange) {
	exchange.setProperty("AttendanceManagement_Job", "Started");
	exchange.setProperty("AttendanceManagement_JobId",
		"AttendanceManagement" + getRandomJobIDNumberFrom(1000, 9999));
	LOGGER.debug("Exchange Created time in Scheduler Processor:{} ", exchange.getProperties());
    }

    public static int getRandomJobIDNumberFrom(int min, int max) {
	Random random = new Random();
	return random.nextInt((max + 1) - min) + min;
    }

}
