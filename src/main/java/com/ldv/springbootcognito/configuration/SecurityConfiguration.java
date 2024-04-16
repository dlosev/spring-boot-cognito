package com.ldv.springbootcognito.configuration;

import com.ldv.springbootcognito.logout.CustomLogoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Value("${aws.cognito.logout.url}")
    private String logoutUrl;

    @Value("${aws.cognito.logout.redirectUrl}")
    private String logoutRedirectUrl;

    @Value("${spring.security.oauth2.client.registration.cognito.clientId}")
    private String clientId;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authz -> authz.requestMatchers("/")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2Login(Customizer.withDefaults())
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutSuccessHandler(
                                new CustomLogoutHandler(logoutUrl, logoutRedirectUrl, clientId)));
        return http.build();
    }

}
