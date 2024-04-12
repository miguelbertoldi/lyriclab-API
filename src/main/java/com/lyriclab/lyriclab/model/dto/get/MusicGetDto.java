package com.lyriclab.lyriclab.model.dto.get;

import com.lyriclab.lyriclab.model.entity.Music;
import com.lyriclab.lyriclab.model.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MusicGetDto {

    private Long id;
    private String title;
    private String artist;
    private Genre genre;
    private LocalDateTime releaseDate;
    private String lyrics;

    public MusicGetDto(Music music) {
        BeanUtils.copyProperties(music, this);
    }

}
