package com.ashok.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import com.ashok.security.CustomUserDetailService;

import com.ashok.security.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig {

	

    @Autowired
    private CustomUserDetailService customUserDetailService;

  
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	
    	 http.csrf(csrf -> csrf.disable())
    	 .authorizeHttpRequests()
		.requestMatchers(AppConstants.PUBLIC_URLS).permitAll()
		.requestMatchers(AppConstants.USER_URLS).hasAnyAuthority("USER", "ADMIN")
		.requestMatchers(AppConstants.ADMIN_URLS).hasAuthority("ADMIN")
		.anyRequest()
		.authenticated()
		.and()
		.exceptionHandling().authenticationEntryPoint(
				(request, response, authException) -> 
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);	
	
	http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	
	http.authenticationProvider(daoAuthenticationProvider());
	
	DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();
	
	return defaultSecurityFilterChain;


    }   


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;

    }


    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


  

}
