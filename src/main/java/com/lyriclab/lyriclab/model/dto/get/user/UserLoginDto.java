package com.lyriclab.lyriclab.model.dto.get.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginDto {

    private String username;
    private String password;

}
