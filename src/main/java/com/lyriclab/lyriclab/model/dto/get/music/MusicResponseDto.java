package com.lyriclab.lyriclab.model.dto.get.music;

import com.lyriclab.lyriclab.model.entity.File;
import com.lyriclab.lyriclab.model.entity.Music;
import com.lyriclab.lyriclab.model.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MusicResponseDto {

    @EqualsAndHashCode.Include
    private Long id;
    private String title;
    private String artist;
    private Genre genre;
    private LocalDateTime releaseDate;
    private String lyrics;
    private String album;
    private File cover;

    public MusicResponseDto(Music music) {
        BeanUtils.copyProperties(music, this);
        this.album = music.getAlbum().getTitle();
        this.cover = music.getAlbum().getCover();
        this.artist = music.getAlbum().getArtist().getFullName();
    }

}
