package com.lyriclab.lyriclab.config;

import com.lyriclab.lyriclab.filter.AuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final SecurityContextRepository securityContextRepository;
    private final AuthFilter authFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(ar -> {
            ar
                .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/music").permitAll()
                .requestMatchers(HttpMethod.GET, "/playlist").permitAll()
                .anyRequest().permitAll();
        });

        setFilter(httpSecurity);
        setDefaultAbstractSettings(httpSecurity);

        return httpSecurity.build();
    }

    private void setDefaultAbstractSettings(HttpSecurity httpSecurity) {
        try {
            httpSecurity.csrf(AbstractHttpConfigurer::disable);
            httpSecurity.formLogin(AbstractHttpConfigurer::disable);
            httpSecurity.logout(AbstractHttpConfigurer::disable);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void setFilter(HttpSecurity httpSecurity) {
        try {
            httpSecurity.securityContext(context -> {
                context
                        .securityContextRepository(securityContextRepository);
            });

            httpSecurity.sessionManagement(config -> {
                config.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            });

            httpSecurity.addFilterBefore
                    (authFilter, UsernamePasswordAuthenticationFilter.class);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
