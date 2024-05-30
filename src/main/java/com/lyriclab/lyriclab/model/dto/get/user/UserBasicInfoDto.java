package com.lyriclab.lyriclab.model.dto.get.user;

import com.lyriclab.lyriclab.model.entity.File;
import com.lyriclab.lyriclab.model.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBasicInfoDto {

    private Long id;
    private String username;
    private File picture;

    public UserBasicInfoDto(User user) {
        BeanUtils.copyProperties(user, this);
    }


}
