package com.lyriclab.lyriclab.service;

import com.lyriclab.lyriclab.model.dto.get.PlaylistResponseDto;
import com.lyriclab.lyriclab.model.dto.post.PlaylistPostDTO;
import com.lyriclab.lyriclab.model.entity.Music;
import com.lyriclab.lyriclab.model.entity.Playlist;
import com.lyriclab.lyriclab.model.entity.user.User;
import com.lyriclab.lyriclab.repository.MusicRepository;
import com.lyriclab.lyriclab.repository.PlaylistRepository;
import com.lyriclab.lyriclab.service.user.UserService;
import com.lyriclab.lyriclab.util.AuthUserUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserService userService;

    private final MusicRepository musicRepository;

    private final AuthUserUtil authUtil;

    public PlaylistResponseDto save(PlaylistPostDTO dto) {
        try {
            User user = authUtil.getAuthenticatedUser();
            Playlist playlist = new Playlist(dto, user);
            if (!Objects.isNull(dto.getMusicIds())) {
                setMusics(playlist, dto.getMusicIds());
            }
            return playlistRepository
                    .save(playlist).toDto();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<PlaylistResponseDto> findByContainingTitlePlaylist(String title){
         List<Playlist> playlists = playlistRepository.findPlaylistByTitleContaining(title);
         return playlists.stream().map(PlaylistResponseDto::new).toList();
    }

    private void setMusics(Playlist playlist, List<Long> ids) {
        playlist.setMusics(
                ids.stream()
                .map(id -> musicRepository.findById(id).get())
                        .toList()
        );
    }

    public List<Playlist> getAll(){
        return playlistRepository.findAll();
    }

    public List<PlaylistResponseDto> findAllByUser() {
        User user = authUtil.getAuthenticatedUser();
        return user.toDto().getPlaylists()
                .stream().filter(p -> !p.getMandatory())
                .toList();
    }
    public void likePlaylistHandler(Long playlistId) {
        User user = authUtil.getAuthenticatedUser();
        Playlist playlist = findById(playlistId);

        if (!user.getPlaylists().contains(playlist)) {
            user.getPlaylists().add(playlist); // liked musics playlist
        } else {
            user.getPlaylists().remove(playlist);
        }

        userService.save(user);
    }
    public Playlist findById(Long id) {
        return playlistRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
    public void delete(Long id) {
        if (!playlistRepository.existsById(id)) {
            throw new RuntimeException("Playlist doesn't exist!");
        }
        playlistRepository.deleteById(id);
    }

    public List<PlaylistResponseDto> findSomeUserPlaylists() {
        List<PlaylistResponseDto> playlists = authUtil.
                getAuthenticatedUser().
                    toDto().getPlaylists();
        return playlists
                .stream()
                .filter(p -> playlists.indexOf(p) > 1)
                .limit(4)
                .toList();
    }

    public void save(Playlist playlist) {
        playlistRepository.save(playlist);
    }
}
