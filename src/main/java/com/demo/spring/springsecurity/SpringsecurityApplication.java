package com.demo.spring.springsecurity;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@SpringBootApplication
public class SpringsecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringsecurityApplication.class, args);
	}

	@Bean
	public CorsFilter crosFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.addAllowedHeader("*");
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("DELETE");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter();
	}
}

