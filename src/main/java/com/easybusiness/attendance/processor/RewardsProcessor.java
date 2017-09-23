package com.easybusiness.attendance.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RewardsProcessor implements Processor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RewardsProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

	LOGGER.info("exchange body is {}", exchange.getIn().getBody().toString());

    }

}
