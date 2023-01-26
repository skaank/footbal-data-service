package com.project.footballApp.config.security;

import com.project.footballApp.filter.JwtAuthFilter;
import com.project.footballApp.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.project.footballApp.constants.AppConstants.PUBLIC_URLS;

/**
 * Security configuration class for defining filter chain ,
 * password encoder and other important security related beans
 * It also manages which all paths should be authenticated and how.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private JwtAuthFilter authFilter;
    private UserInfoRepository userInfoRepository;


    @Autowired
    public SecurityConfig(JwtAuthFilter authFilter, UserInfoRepository userInfoRepository) {
        this.authFilter = authFilter;
        this.userInfoRepository = userInfoRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService(this.userInfoRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        return http.csrf().disable()
                .authorizeHttpRequests().requestMatchers("/authenticate").permitAll().and()
                .authorizeHttpRequests().requestMatchers(PUBLIC_URLS).permitAll().and()
                .authorizeHttpRequests().requestMatchers("/users/**").authenticated().and()
                .authorizeHttpRequests().requestMatchers("/country/**").authenticated().and()
                .authorizeHttpRequests().requestMatchers("/teams/**").authenticated().and()
                .authorizeHttpRequests().requestMatchers("/competitions/**").authenticated().and()
                .authorizeHttpRequests().requestMatchers("/standings/**").authenticated().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
