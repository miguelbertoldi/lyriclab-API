package com.lyriclab.lyriclab.model.dto.get.user;

import com.lyriclab.lyriclab.model.dto.get.PlaylistResponseDto;
import com.lyriclab.lyriclab.model.entity.File;
import com.lyriclab.lyriclab.model.entity.Playlist;
import com.lyriclab.lyriclab.model.entity.user.User;
import com.lyriclab.lyriclab.model.enums.UserKind;
import com.lyriclab.lyriclab.model.interfaces.IUserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto implements IUserResponse {

    private Long id;
    private String username;
    private String description;
    private String email;
    private String fullName;
    private UserKind userKind;
    private List<PlaylistResponseDto> playlists;
    private File picture;

    public UserResponseDto(User user) {
        BeanUtils.copyProperties(user, this);
        this.playlists = convertPlaylistsToDto(user.getPlaylists());
    }

    private List<PlaylistResponseDto> convertPlaylistsToDto(List<Playlist> playlists) {
        return playlists
                .stream()
                .map(PlaylistResponseDto::new)
                .toList();
    }

}
