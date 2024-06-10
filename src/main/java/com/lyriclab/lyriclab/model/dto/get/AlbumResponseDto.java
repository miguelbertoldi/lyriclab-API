package com.lyriclab.lyriclab.model.dto.get;

import com.lyriclab.lyriclab.model.dto.get.music.MusicResponseDto;
import com.lyriclab.lyriclab.model.entity.Album;
import com.lyriclab.lyriclab.model.entity.File;
import com.lyriclab.lyriclab.model.entity.Music;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumResponseDto {

    private Long id;
    private String title;
    private String artist;
    private List<MusicResponseDto> musics;
    private File cover;

    public AlbumResponseDto(Album album) {
        BeanUtils.copyProperties(album, this);
        this.musics = convertMusicsToDto(album.getMusics());
    }

    private List<MusicResponseDto> convertMusicsToDto(List<Music> musics) {
        return musics
                .stream()
                .map(MusicResponseDto::new)
                .toList();
    }

}
