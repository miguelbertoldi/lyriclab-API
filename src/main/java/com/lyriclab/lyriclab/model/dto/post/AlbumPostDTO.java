package com.lyriclab.lyriclab.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumPostDTO {

    @NonNull
    private String title;
    @NonNull
    private Long artistId;

}
