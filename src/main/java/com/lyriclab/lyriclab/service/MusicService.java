package com.lyriclab.lyriclab.service;

import com.lyriclab.lyriclab.model.dto.get.music.MusicResponseDto;
import com.lyriclab.lyriclab.model.dto.get.music.MusicPlayDto;
import com.lyriclab.lyriclab.model.dto.post.MusicPostDTO;
import com.lyriclab.lyriclab.model.entity.Album;
import com.lyriclab.lyriclab.model.entity.Music;
import com.lyriclab.lyriclab.model.entity.Playlist;
import com.lyriclab.lyriclab.model.entity.user.User;
import com.lyriclab.lyriclab.model.enums.Genre;
import com.lyriclab.lyriclab.repository.MusicRepository;
import com.lyriclab.lyriclab.service.user.UserService;
import com.lyriclab.lyriclab.util.AuthUserUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class MusicService {

    private final MusicRepository musicRepository;
    private final AlbumService albumService;
    private final PlaylistService playlistService;
    private final FileService fileService;
    private final UserService userService;

    private final AuthUserUtil authUtil;

    public MusicResponseDto save(MusicPostDTO dto, Long albumId) {
        try {
            Album album = albumService.findEntityById(albumId);
            Music music = new Music(dto, album);
            return musicRepository
                    .save(music).toDto();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public MusicResponseDto save(MusicPostDTO dto) {
        try {
            Album album = albumService.createMusicAlbum(dto);
            Music music = musicRepository.save(new Music(dto, album));
            return music.toDto();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Music findById(Long id) {
        return musicRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<MusicResponseDto> findRecent() {
        User user = authUtil.getAuthenticatedUser();
        return user.getPlaylists().get(1)
                .toDto().getMusics();
    }

    public List<MusicResponseDto> findSystemMostRecent() {
        List<Music> musics = musicRepository.findAll();
        Collections.reverse(musicRepository.findAll());
        return musics.stream()
                .map(MusicResponseDto::new)
                .limit(25).toList();
    }


    public MusicPlayDto playMusic(Long musicId) {
        User user = authUtil.getAuthenticatedUser();
        Music music = findById(musicId);
        Boolean isLiked = user.getPlaylists().get(0).getMusics().contains(music);

        Playlist playlist = user.getPlaylists().get(1); // recently played playlist

        if (playlist.getMusics().size() > 11) {
            playlist.getMusics().remove(0);
        }

        playlist.getMusics().remove(music);
        playlistService.save(playlist);

        playlist.getMusics().add(music);

        playlistService.save(playlist);
        return music.toPlayDto(isLiked);
    }

    public void likeMusicHandler(Long musicId) {
        User user = authUtil.getAuthenticatedUser();
        Music music = findById(musicId);
        Playlist playlist = user.getPlaylists().get(0); // liked musics playlist

        if (!playlist.getMusics().contains(music)) {
            playlist.getMusics().add(music);
        } else {
            playlist.getMusics().remove(music);
        }

        playlistService.save(playlist);
    }

    public void delete(Music music) {
        if (!musicRepository.existsById(music.getId())) {
            throw new RuntimeException("Doesn't exists!");
        }
        musicRepository.delete(music);
    }

    public MusicResponseDto uploadFile(MultipartFile file, Long id) {
        try {
            Music music = findById(id);
            music.setFile(fileService.save(file));

            return musicRepository.save(music).toDto();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public MusicPlayDto findNextMusicHistory(Long currentMusicId) {
        Genre currentMusicGenre = findById(currentMusicId).getGenre();
        User user = authUtil.getAuthenticatedUser();
        Music music = musicRepository.findRandomByGenre(currentMusicGenre, currentMusicId);
        Boolean isLiked = user.getPlaylists().get(0).getMusics().contains(music);
        return music.toPlayDto(isLiked);
    }

    public List<MusicResponseDto> findAll() {
        return musicRepository.findAll()
                .stream().map(MusicResponseDto::new)
                .toList();
    }

    public List<MusicResponseDto> findSomeToAddPlaylist() {
        return authUtil.getAuthenticatedUser()
                .toDto().getPlaylists()
                .stream().flatMap(p -> p.getMusics().stream())
                .distinct()
                .toList();
    }
}
