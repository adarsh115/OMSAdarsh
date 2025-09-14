package com.adarsh.oms.oms_risk_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class OmsRiskServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmsRiskServiceApplication.class, args);
	}

}
