package com.excilys.cdb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LOGGER.info("Configure HttpSecurity");
        http.authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/dashboard")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .permitAll()
            .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll()
            .and()
                .exceptionHandling().accessDeniedPage("/errors/403");

        LOGGER.debug("antMatchers : /resources/**");
        LOGGER.debug("anyRequest : authentificated");
        LOGGER.debug("formLogin : permitAll");
        LOGGER.debug("loginPage : /login");
        LOGGER.debug("defaultSucessUrl : /dashboard");
        LOGGER.debug("usernameParameter : username");
        LOGGER.debug("passwordParameter : password");
        LOGGER.debug("logout : permitAll");
        LOGGER.debug("accessDeniedPage : /errors/403");
    }

    /**
     * Gets the user details service.
     *
     * @return user details service
     */
    @Bean
    public UserDetailsService userDetailsService() {
        LOGGER.info("UserDetailsService initialization");
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password("admin").roles("ADMIN").build());

        return manager;
    }
}
