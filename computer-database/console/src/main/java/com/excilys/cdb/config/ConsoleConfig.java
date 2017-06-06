package com.excilys.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.excilys.cdb.cli.impl.CompanyConsole;

@Configuration
@Import(ServiceConfig.class)
@ComponentScan(basePackageClasses=CompanyConsole.class)
public class ConsoleConfig {

}