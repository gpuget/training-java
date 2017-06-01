package com.excilys.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.excilys.cdb.mapper.dto.CompanyMapper;

@Configuration
@ComponentScan(basePackageClasses = CompanyMapper.class)
public class BindingConfig {

}
