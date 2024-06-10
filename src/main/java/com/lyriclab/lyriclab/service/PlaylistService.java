package com.lyriclab.lyriclab.service;

import com.lyriclab.lyriclab.model.dto.get.PlaylistResponseDto;
import com.lyriclab.lyriclab.model.dto.post.PlaylistPostDTO;
import com.lyriclab.lyriclab.model.entity.Playlist;
import com.lyriclab.lyriclab.model.entity.user.User;
import com.lyriclab.lyriclab.repository.PlaylistRepository;
import com.lyriclab.lyriclab.service.user.UserService;
import com.lyriclab.lyriclab.util.AuthUserUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserService userService;

    private final AuthUserUtil authUtil;

    public PlaylistResponseDto save(PlaylistPostDTO dto) {
        try {
            User user = authUtil.getAuthenticatedUser();
            Playlist playlist = new Playlist(dto, user);
            return playlistRepository
                    .save(playlist).toDto();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<PlaylistResponseDto> findAllByUser() {
        User user = authUtil.getAuthenticatedUser();
        return user.toDto().getPlaylists()
                .stream().filter(p -> !p.getMandatory())
                .toList();
    }


    public void delete(Long id) {
        if (!playlistRepository.existsById(id)) {
            throw new RuntimeException("Playlist doesn't exist!");
        }
        playlistRepository.deleteById(id);
    }

    public void save(Playlist playlist) {
        playlistRepository.save(playlist);
    }
}
