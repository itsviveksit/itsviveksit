package com.org.khatabahi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan(
		basePackages = {"com.org.khatabahi"}
)

@ImportResource({"classpath:camel-starter-IN.xml"})
public class CamelApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CamelApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(CamelApplication.class, args);
	}

}
