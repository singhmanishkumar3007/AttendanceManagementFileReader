package com.easybusiness.attendance.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.easybusiness.attendance.processor.StopProcessor;

@Component
public class StopRoute extends RouteBuilder {

    @Autowired
    private  StopProcessor stopProcessor;

    @Override
    public void configure() throws Exception {
	from(RouteConstant.STOP_ROUTE).routeId(RouteConstant.STOP_ROUTE).process(stopProcessor).end();
    }
}
