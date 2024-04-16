package com.lyriclab.lyriclab.config;

import com.lyriclab.lyriclab.util.AuthFilter;
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

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(ar -> {
            ar
                .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/auth/teste").permitAll()
                .anyRequest().authenticated();
        });

        httpSecurity.securityContext(context -> {
            context
                    .securityContextRepository(securityContextRepository);
        });

        httpSecurity.formLogin(AbstractHttpConfigurer::disable);
        httpSecurity.logout(AbstractHttpConfigurer::disable);

        httpSecurity.sessionManagement(config -> {
            config.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        httpSecurity.addFilterBefore
                (authFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }


}
