package com.lyriclab.lyriclab.util;

import com.lyriclab.lyriclab.model.entity.user.User;
import com.lyriclab.lyriclab.model.entity.user.UserDetailsEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthUserUtil {

    public User getAuthenticatedUser() {
        return ((UserDetailsEntity)
                SecurityContextHolder
                        .getContext().getAuthentication()
                        .getPrincipal()).getUser();
    }

}
