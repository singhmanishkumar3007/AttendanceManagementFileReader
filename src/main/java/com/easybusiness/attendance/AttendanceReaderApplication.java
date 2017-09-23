package com.easybusiness.attendance;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = { "com.easybusiness.attendance", "com.easybusiness.attendancepersistence" })
@EnableAutoConfiguration
public class AttendanceReaderApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttendanceReaderApplication.class);
    
    @Value("${attendancemanagement.corepoolsize.multicast}")
    private int multicastCorePoolSize;

    @Value("${attendancemanagement.maxpoolsize.multicast}")
    private int multicastMaxPoolSize;

    public static void main(String[] args) {
	SpringApplication.run(AttendanceReaderApplication.class, args);
	LOGGER.info("started AttendanceManagementFileReaderApplication");
    }

    @Override
    public void run(String... args) throws Exception {
	LOGGER.info("in run method");

    }

   

    @Bean
    RestTemplate restTemplate() {
	return new RestTemplate();
    }

    @Bean
    ExecutorService fixedThreadPool() {
	return new ThreadPoolExecutor(multicastCorePoolSize, multicastMaxPoolSize, 0L, TimeUnit.MILLISECONDS,
		new LinkedBlockingQueue<Runnable>());
    }

    @Bean
    HttpHeaders httpHeaders() {
	return new HttpHeaders();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	return new PropertySourcesPlaceholderConfigurer();
    }
}