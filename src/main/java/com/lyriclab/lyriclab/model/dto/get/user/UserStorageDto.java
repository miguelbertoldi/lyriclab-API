package com.lyriclab.lyriclab.model.dto.get.user;

import com.lyriclab.lyriclab.model.entity.user.User;
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

    public UserStorageDto(User user) {
        BeanUtils.copyProperties(user, this);
    }

}
