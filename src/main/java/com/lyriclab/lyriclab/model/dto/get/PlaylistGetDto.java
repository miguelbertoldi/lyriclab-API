package com.lyriclab.lyriclab.model.dto.get;

import com.lyriclab.lyriclab.model.dto.get.music.MusicGetDto;
import com.lyriclab.lyriclab.model.entity.Music;
import com.lyriclab.lyriclab.model.entity.Playlist;
import com.lyriclab.lyriclab.model.enums.PlaylistType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaylistGetDto {

    private Long id;
    private String title;
    private String description;
    private List<MusicGetDto> musics;
    private Boolean mandatory;
    private PlaylistType type;

    public PlaylistGetDto(Playlist playlist) {
        BeanUtils.copyProperties(playlist, this);
        this.musics = convertMusicsToDto(playlist.getMusics());
    }

    private List<MusicGetDto> convertMusicsToDto(List<Music> musics) {
        return musics
                .stream()
                .map(MusicGetDto::new)
                .toList();
    }

}
