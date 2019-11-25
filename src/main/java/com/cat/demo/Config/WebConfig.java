package com.cat.demo.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    /**
     * Log
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
    }

}
