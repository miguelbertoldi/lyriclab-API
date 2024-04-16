package com.lyriclab.lyriclab.util;

import com.lyriclab.lyriclab.service.user.UserDetailsFinderService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private SecurityContextRepository contextRespository;
    private final CookieUtil cookieUtil;
    private final JwtUtil jwtUtil;
    private final UserDetailsFinderService userDetailsService;

    @Override
    public void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    ) throws ServletException, IOException {
        if (!isPublicRoute(req)) {
            try {
                Cookie cookie = cookieUtil.getCookie(req, "JWT");
                String token = cookie.getValue();
                String username = jwtUtil.getUsername(token);

                UserDetails ud = userDetailsService
                        .loadUserByUsername(username);

                Authentication auth =
                        new UsernamePasswordAuthenticationToken(
                                ud,
                                ud.getPassword(),
                                ud.getAuthorities());

                SecurityContext context
                        = SecurityContextHolder.createEmptyContext();

                context.setAuthentication(auth);
                contextRespository.saveContext(context, req, res);

                Cookie renovatedCookie
                        = cookieUtil.generateCookie(ud);

                res.addCookie(renovatedCookie);
            } catch (Exception e) {
                res.setStatus(401);
            }
        }

        chain.doFilter(req, res);
    }

    private boolean isPublicRoute(HttpServletRequest req) {
        return req.getRequestURI().contains("login");
    }

}
