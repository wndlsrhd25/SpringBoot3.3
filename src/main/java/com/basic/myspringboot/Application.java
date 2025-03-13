package com.basic.myspringboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		//SpringApplication.run(Application.class, args);
		SpringApplication application = new SpringApplication(Application.class);

		//WebApplication 의 타입 변경
		/*
		 NONE, - 웹어플리케이션이 아님 (tomcat 동작하지 않음)
   		 SERVLET, (default) springMVC
    	 REACTIVE; WebFlux (async, non-blocking)
		 */
		application.setWebApplicationType(WebApplicationType.SERVLET); //웹어플리케이션 타입 지정
		application.run(args);
	}

	@Bean
	public String hello() {
		return "Helllo Springboot";
	}
}

