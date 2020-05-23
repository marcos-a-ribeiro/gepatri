package com.gepatri;

import javax.faces.webapp.FacesServlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

//@SpringBootApplication
@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
public class GepatriApplication {

	public static void main(String[] args) {
		SpringApplication.run(GepatriApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		FacesServlet servlet = new FacesServlet();
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "*.xhtml");
		return servletRegistrationBean;
	}
}
