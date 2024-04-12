package com.lyriclab.lyriclab.model.dto.post;

import com.lyriclab.lyriclab.model.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class MusicCreationDTO {

    @NonNull
    private String title;
    @NonNull
    private String artist;
    @NonNull
    private Genre genre;
    private LocalDate releaseDate;
    private String lyrics;

}
