package com.easybusiness.attendance.processor;

import static com.easybusiness.attendance.routes.RouteConstant.ATTENDANCE_READER_ROUTE_ID;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StopProcessor implements Processor {
    private static final Logger LOGGER = LoggerFactory.getLogger(StopProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
	LOGGER.debug("File processor started here...");
	exchange.getContext().stopRoute(ATTENDANCE_READER_ROUTE_ID);
	LOGGER.debug("File processor ended here...");
    }
}
