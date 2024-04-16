package com.lyriclab.lyriclab.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    public String generateToken(UserDetails ud) {
        Algorithm algorithm = Algorithm.HMAC256("123");
        return JWT.create()
                .withIssuer("LyricLab")
                .withIssuedAt(new Date())
                    .withExpiresAt(new Date(new Date().getTime() + 300000))
                .withSubject(ud.getUsername())
                .sign(algorithm);
    }

    public String getUsername(String token) {
        return JWT
                .decode(token)
                .getSubject();
    }

}
