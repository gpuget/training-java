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
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
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
                .exceptionHandling().accessDeniedPage("/errors/403")
            .and()
                .exceptionHandling().authenticationEntryPoint(digestAuthEntryPoint())
            .and()
                .addFilter(digestAuthFilter(digestAuthEntryPoint()));
        LOGGER.debug("antMatchers : /resources/**");
        LOGGER.debug("anyRequest : authentificated");
        LOGGER.debug("formLogin : permitAll");
        LOGGER.debug("loginPage : /login");
        LOGGER.debug("defaultSucessUrl : /dashboard");
        LOGGER.debug("usernameParameter : username");
        LOGGER.debug("passwordParameter : password");
        LOGGER.debug("logout : permitAll");
        LOGGER.debug("accessDeniedPage : /errors/403");
        LOGGER.debug("authenticationEntryPoint : digestAuthEntryPoint");
        LOGGER.debug("addFilter : digestAuthFilter");
    }

    @Bean
    public UserDetailsService userDetailsService() {
        LOGGER.info("UserDetailsService initialization");
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password("admin").roles("ADMIN").build());
        
        return manager;
    }
    
    @Bean
    public DigestAuthenticationFilter digestAuthFilter(DigestAuthenticationEntryPoint digestAuthEntryPoint) {
        LOGGER.info("DigestAuthenticationFilter initialization");
        DigestAuthenticationFilter digestAuthFilter = new DigestAuthenticationFilter();
        
        digestAuthFilter.setUserDetailsService(userDetailsService());
        digestAuthFilter.setAuthenticationEntryPoint(digestAuthEntryPoint);
        
        return digestAuthFilter;
    }
    
    @Bean
    public DigestAuthenticationEntryPoint digestAuthEntryPoint() {
        LOGGER.info("DigestAuthenticationEntryPoint initialization");
        DigestAuthenticationEntryPoint entrypoint = new DigestAuthenticationEntryPoint();
        
        String realmName = "Contacts Realm via Digest Authentication";
        String key = "acegi";
        entrypoint.setRealmName(realmName);
        LOGGER.debug("Realm name : " + realmName);
        entrypoint.setKey(key);
        LOGGER.debug("Key : " + key);
        entrypoint.setNonceValiditySeconds(10);
        LOGGER.debug("Nonce validity : 10 s");
        
        return entrypoint;
    }
}
