package com.lyriclab.lyriclab.service.user;

import com.lyriclab.lyriclab.model.entity.user.User;
import com.lyriclab.lyriclab.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsFinderService
        implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userService.findEntityByUsername(username);
        return user.getUserDetails();
    }


}
