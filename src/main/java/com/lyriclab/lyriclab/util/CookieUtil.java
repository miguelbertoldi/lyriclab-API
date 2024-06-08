package com.lyriclab.lyriclab.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.util.Objects;

@Component
public class CookieUtil {

    public Cookie generateCookie(UserDetails ud) {
        String token = new JwtUtil().generateToken(ud);
        Cookie cookie = new Cookie("JWT", token);
        cookie.setPath("/");
        cookie.setMaxAge(720);
        return cookie;
    }

    public Cookie getCookie(
            HttpServletRequest req, String name) {
        Cookie cookie = WebUtils.getCookie(req, name);
        if (!Objects.isNull(cookie)) return cookie;
        throw new RuntimeException("Cookie not found");
    }

    public Cookie generateNullCookie() {
        Cookie cookie = new Cookie("JWT", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        return cookie;
    }

    public Cookie removeContext() {
        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        return cookie;
    }

}
