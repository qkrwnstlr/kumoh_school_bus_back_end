package com.example.kumoh_school_bus.config;

import com.example.kumoh_school_bus.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  WebSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception{
    http.cors()
        .and()
        .csrf()
        .disable()
        .httpBasic()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/","/auth/**").permitAll()
        .anyRequest()
        .authenticated();

    http.addFilterBefore(jwtAuthenticationFilter, CorsFilter.class);
  }
}
