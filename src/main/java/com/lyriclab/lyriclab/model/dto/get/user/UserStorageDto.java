package com.lyriclab.lyriclab.model.dto.get.user;

import com.lyriclab.lyriclab.model.entity.user.User;
import com.lyriclab.lyriclab.model.enums.UserKind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserStorageDto {

    private Long id;
    private String username;
    private String fullName;
    private UserKind userKind;

    public UserStorageDto(User user) {
        BeanUtils.copyProperties(user, this);
    }

}
