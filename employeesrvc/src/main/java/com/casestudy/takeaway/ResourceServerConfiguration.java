package com.casestudy.takeaway;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
/**
 * This method Configure's This as a resource .
 * Client having write access can only access secured API's
 * @author Admin
 *
 */
@EnableResourceServer
@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{

	
    @Override
    public void configure(HttpSecurity http) throws Exception {
    	
        http.
        anonymous().disable()
        .requestMatchers().antMatchers("/secured/**")
        .and().authorizeRequests()
        .antMatchers("/secured/**").access("#oauth2.hasScope('write')")
        .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
       
    }
}
