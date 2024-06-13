package com.lyriclab.lyriclab.model.dto.get.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordDTO {

    private String email;
    private String password;
}
