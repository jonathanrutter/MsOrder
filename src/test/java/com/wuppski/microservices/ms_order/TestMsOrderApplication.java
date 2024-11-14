package com.wuppski.microservices.ms_order;

import org.springframework.boot.SpringApplication;

public class TestMsOrderApplication {

	public static void main(String[] args) {
		SpringApplication.from(MsOrderApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
