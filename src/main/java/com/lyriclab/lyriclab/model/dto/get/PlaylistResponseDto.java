package com.lyriclab.lyriclab.model.dto.get;

import com.lyriclab.lyriclab.model.dto.get.music.MusicResponseDto;
import com.lyriclab.lyriclab.model.entity.Music;
import com.lyriclab.lyriclab.model.entity.Playlist;
import com.lyriclab.lyriclab.model.entity.user.User;
import com.lyriclab.lyriclab.model.enums.PlaylistType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaylistResponseDto {

    private Long id;
    private String title;
    private String description;
    private String owner;
    private List<MusicResponseDto> musics;
    private Boolean mandatory;
    private PlaylistType type;
//    private User owner;

    public PlaylistResponseDto(Playlist playlist) {
        BeanUtils.copyProperties(playlist, this);
        this.owner = playlist.getOwner().getFullName();
        this.musics = convertMusicsToDto(playlist.getMusics());
    }

    private List<MusicResponseDto> convertMusicsToDto(List<Music> musics) {
        return musics
                .stream()
                .map(MusicResponseDto::new)
                .toList();
    }

}
