package com.excilys.cdb.config;

import java.util.Locale;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.excilys.cdb.controller.AddComputerController;

@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses = AddComputerController.class)
public class WebConfig extends WebMvcConfigurerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        LOGGER.info("RedirectViewController : '/' to '/dashboard'");
        registry.addRedirectViewController("/", "/dashboard");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        LOGGER.info("Web config : addResourceHandlers");
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    /**
     * Gets the view resolver.
     *
     * @return view resolver
     */
    @Bean
    public ViewResolver internalRessourceViewResolver() {
        LOGGER.info("ViewResolver initialization");
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setSuffix(".jsp");
        LOGGER.debug("class : JstlView.class");
        LOGGER.debug("suffix : .jsp");

        return resolver;
    }

    /**
     * Gets the exception handler resolver.
     *
     * @return exception handler resolver
     */
    @Bean
    public HandlerExceptionResolver handlerExceptionResolver() {
        LOGGER.info("HandlerExceptionResolver initialization");
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        Properties exceptions = new Properties();
        exceptions.setProperty("NoHandlerFoundException", "/errors/404");
        resolver.setExceptionMappings(exceptions);
        resolver.setDefaultErrorView("/errors/500");
        return resolver;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("/WEB-INF/messages/usermsg");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(new Locale("fr"));
        resolver.setCookieName("myLocaleCookie");
        resolver.setCookieMaxAge(4800);
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("mylocale");
        registry.addInterceptor(interceptor);
    }
}
