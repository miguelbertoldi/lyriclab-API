package com.lyriclab.lyriclab.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistCreationDTO {

    @NonNull
    private String title;

    @NonNull
    private String description;

}