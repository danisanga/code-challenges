package com.danisanga.infrastructure.configuration;

import com.danisanga.application.responses.DispenserStatsResponse;
import com.danisanga.domain.models.DispenserStatsModel;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class BaseConfiguration {

    @Resource
    private AbstractConverter<DispenserStatsModel, DispenserStatsResponse> dispenserStatsConverter;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(dispenserStatsConverter);
        return modelMapper;
    }
}
