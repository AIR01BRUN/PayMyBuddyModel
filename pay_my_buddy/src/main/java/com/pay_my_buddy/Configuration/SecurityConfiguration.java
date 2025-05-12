/*
 * package com.pay_my_buddy.Configuration;
 * 
 * import org.springframework.context.annotation.Bean;
 * import org.springframework.context.annotation.Configuration;
 * import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.web.SecurityFilterChain;
 * 
 * @Configuration
 * public class SecurityConfiguration {
 * 
 * @Bean
 * public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
 * Exception {
 * http
 * .authorizeHttpRequests()
 * .requestMatchers("/login").permitAll()
 * .and()
 * .formLogin()
 * .loginPage("/login")
 * .failureUrl("/login?error=true");
 * 
 * return http.build();
 * 
 * }
 * 
 * }
 */
