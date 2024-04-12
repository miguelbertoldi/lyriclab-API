package com.lyriclab.lyriclab.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumCreationDTO {

    @NonNull
    private String title;
    @NonNull
    private String artist;

}
