package com.lyriclab.lyriclab.model.dto.get.user;

import com.lyriclab.lyriclab.model.dto.get.AlbumResponseDto;
import com.lyriclab.lyriclab.model.dto.get.PlaylistResponseDto;
import com.lyriclab.lyriclab.model.entity.Album;
import com.lyriclab.lyriclab.model.entity.File;
import com.lyriclab.lyriclab.model.entity.Playlist;
import com.lyriclab.lyriclab.model.entity.user.User;
import com.lyriclab.lyriclab.model.interfaces.IUserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArtistResponseDTO implements IUserResponse {

    private Long id;
    private String username;
    private String description;
    private String email;
    private String fullName;
    private List<AlbumResponseDto> albums;
    private File picture;

    public ArtistResponseDTO(User user) {
        BeanUtils.copyProperties(user, this);
        this.albums = convertAlbumsToDto(user.getAlbums());
    }

    private List<AlbumResponseDto> convertAlbumsToDto(List<Album> albums) {
        return albums
                .stream()
                .map(AlbumResponseDto::new)
                .toList();
    }

}
