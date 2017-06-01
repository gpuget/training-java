package com.excilys.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.excilys.cdb.service.CompanyService;

@Configuration
@ComponentScan(basePackageClasses = CompanyService.class)
@Import({DataConfig.class, BindingConfig.class})
public class ServiceConfig {

}
