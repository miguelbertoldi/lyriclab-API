package com.lyriclab.lyriclab.service.user;

import com.lyriclab.lyriclab.model.dto.get.user.UserGetDto;
import com.lyriclab.lyriclab.model.dto.get.user.UserLoginDto;
import com.lyriclab.lyriclab.model.dto.get.user.UserStorageDto;
import com.lyriclab.lyriclab.model.dto.post.UserCreationDTO;
import com.lyriclab.lyriclab.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager manager;
    private final CookieUtil cookieUtil;
    private final UserService userService;

    public UserGetDto register(UserCreationDTO dto) {
        if (userService.existsByEmailAndUsername(
                dto.getUsername(), dto.getEmail())) {
            throw new RuntimeException("Email or username already registered");
        }
        return userService.save(dto);
    }

    public UserStorageDto login(UserLoginDto dto,
                      HttpServletRequest req,
                      HttpServletResponse res) {
        try {
            UsernamePasswordAuthenticationToken token
                    = new UsernamePasswordAuthenticationToken(
                            dto.getUsername(), dto.getPassword()
                    );
            Authentication auth = manager.authenticate(token);
            UserDetails ud = (UserDetails) auth.getPrincipal();

            Cookie cookie = cookieUtil.generateCookie(ud);
            res.addCookie(cookie);
            return findUser(dto);

        } catch (Exception e) {
            throw new RuntimeException("Exception");
        }
    }

    private UserStorageDto findUser(UserLoginDto dto) {
        return new UserStorageDto(
                userService.findEntityByUsername
                    (dto.getUsername()));
    }

    public void logout(HttpServletRequest req,
                       HttpServletResponse res) {
        res.addCookie(cookieUtil.removeContext());
        res.addCookie(cookieUtil.generateNullCookie());
    }

}
