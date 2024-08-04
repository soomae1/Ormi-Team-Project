package com.example.journeyednt.security;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationFailureHandler failureHandler, AccessDeniedHandler accessDeniedHandler) throws Exception {
        http.authorizeHttpRequests(auth ->
        {
            auth.dispatcherTypeMatchers(DispatcherType.ERROR, DispatcherType.FORWARD).permitAll();
            auth.requestMatchers(HttpMethod.GET, "/", "/index", "admin/notice/**", "/error").permitAll();
            auth.requestMatchers("/users/login").permitAll();
            auth.requestMatchers(HttpMethod.POST, "/users", "/users/**").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/users", "/users/**").hasAnyRole("User");
            auth.requestMatchers("/admin", "/admin/**").hasRole("Admin");
            auth.anyRequest().hasAnyRole("User");
        });

        http.formLogin(cnf -> cnf.loginPage("/users/login").usernameParameter("accountId").passwordParameter("password").permitAll().failureHandler(failureHandler).defaultSuccessUrl("/index"));
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.logout(conf -> conf.logoutUrl("/users/logout").permitAll().logoutSuccessUrl("/index"));

        http.exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(accessDeniedHandler));

        return http.build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("Admin").implies("User")
                .build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
        accessDeniedHandler.setErrorPage("/error");
        return accessDeniedHandler;
    }
}
