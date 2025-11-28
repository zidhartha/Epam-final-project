package com.epam.rd.autocode.assessment.appliances.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//created this class since ModelMapper could not be used with maven dependency only unfortunately.
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper ModelMapperConfig(){
        return new ModelMapper();
    }
}
