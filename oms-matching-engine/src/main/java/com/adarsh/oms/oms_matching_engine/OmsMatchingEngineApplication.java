package com.adarsh.oms.oms_matching_engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class OmsMatchingEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmsMatchingEngineApplication.class, args);
	}

}
