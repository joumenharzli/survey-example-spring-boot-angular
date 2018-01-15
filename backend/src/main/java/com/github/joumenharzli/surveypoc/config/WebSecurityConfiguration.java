package com.github.joumenharzli.surveypoc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * WebSecurity Configuration
 *
 * @author Joumen HARZLI
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  /**
   * Override this method to configure the {@link HttpSecurity}. Typically subclasses
   * should not invoke this method by calling super as it may override their
   * configuration. The default configuration is:
   * <p>
   * <pre>
   * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
   * </pre>
   *
   * @param http the {@link HttpSecurity} to modify
   * @throws Exception if an error occurs
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/v2/api-docs/**", "/swagger-resources/configuration/ui", "/swagger-ui.html").permitAll();
  }
}
