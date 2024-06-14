package com.lyriclab.lyriclab.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistPostDTO {

    @NonNull
    private String title;

    @NonNull
    private String description;

    private List<Long> musicIds;

}
