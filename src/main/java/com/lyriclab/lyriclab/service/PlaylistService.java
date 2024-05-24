package com.lyriclab.lyriclab.service;

import com.lyriclab.lyriclab.model.dto.get.PlaylistGetDto;
import com.lyriclab.lyriclab.model.dto.post.PlaylistCreationDTO;
import com.lyriclab.lyriclab.model.entity.Playlist;
import com.lyriclab.lyriclab.model.entity.user.User;
import com.lyriclab.lyriclab.repository.PlaylistRepository;
import com.lyriclab.lyriclab.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserService userService;

    public PlaylistGetDto save(PlaylistCreationDTO dto, Long userId) {
        try {
            User user = userService.findEntityById(userId);
            Playlist playlist = new Playlist(dto, user);
            return playlistRepository
                    .save(playlist).toDto();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<PlaylistGetDto> findAllByUser(Long id) {
        return playlistRepository.findAllByOwner_Id(id)
                .stream().map(PlaylistGetDto::new)
                    .toList();
    }


    public void delete(Long id) {
        if (!playlistRepository.existsById(id)) {
            throw new RuntimeException("Playlist doesn't exist!");
        }
        playlistRepository.deleteById(id);
    }
}
