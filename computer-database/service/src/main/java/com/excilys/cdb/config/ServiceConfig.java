package com.excilys.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.excilys.cdb.mapper.dto.CompanyMapper;
import com.excilys.cdb.service.CompanyService;

@Configuration
@ComponentScan(basePackageClasses = { CompanyService.class, CompanyMapper.class })
@Import(DataConfig.class)
public class ServiceConfig {

}
