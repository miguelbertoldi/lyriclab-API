package com.lyriclab.lyriclab.service;

import com.lyriclab.lyriclab.model.dto.get.PlaylistGetDto;
import com.lyriclab.lyriclab.model.dto.post.PlaylistCreationDTO;
import com.lyriclab.lyriclab.model.entity.Playlist;
import com.lyriclab.lyriclab.model.entity.user.User;
import com.lyriclab.lyriclab.repository.PlaylistRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    public PlaylistGetDto save(PlaylistCreationDTO dto, User user) {
        try {
            Playlist playlist = new Playlist(dto, user);
            return savePlaylistAndConvertToDto(playlist);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private PlaylistGetDto savePlaylistAndConvertToDto(Playlist playlist) {
        return new PlaylistGetDto(
                playlistRepository.save(playlist)
        );
    }
}
