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
            playlistRepository.save(playlist);
            return playlist.toDto();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void delete(Long id) {
        if (!playlistRepository.existsById(id)) {
            throw new RuntimeException("Playlist doesn't exist!");
        }
        playlistRepository.deleteById(id);
    }
}
