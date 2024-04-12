package com.lyriclab.lyriclab.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreationDTO {

    @NonNull
    private String username;
    @NonNull
    private String fullName;
    @NonNull
    private String email;
    @NonNull
    private String password;

}
