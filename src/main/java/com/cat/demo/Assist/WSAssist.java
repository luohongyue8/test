package com.cat.demo.Assist;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:service.properties")
public class WSAssist {

    @Value("${urlWeather}")
    public String URL ;

    @Value("${api}")
    public String API ;

    @Value("${urlCats}")
    public String URLCAT ;

//    @Value("${wsUrl}")
//    public String WSURL ;
}
