package com.lyriclab.lyriclab.model.dto.get.user;

import com.lyriclab.lyriclab.model.dto.get.PlaylistGetDto;
import com.lyriclab.lyriclab.model.entity.File;
import com.lyriclab.lyriclab.model.entity.Playlist;
import com.lyriclab.lyriclab.model.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGetDto {

    private Long id;
    private String username;
    private String email;
    private String fullName;
    private List<PlaylistGetDto> playlists;
    private File picture;

    public UserGetDto(User user) {
        BeanUtils.copyProperties(user, this);
        this.playlists = convertPlaylistsToDto(user.getPlaylists());
    }

    private List<PlaylistGetDto> convertPlaylistsToDto(List<Playlist> playlists) {
        return playlists
                .stream()
                .map(PlaylistGetDto::new)
                .toList();
    }

}
