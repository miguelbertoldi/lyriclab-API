package com.lyriclab.lyriclab.service;

import com.lyriclab.lyriclab.model.dto.get.music.MusicGetDto;
import com.lyriclab.lyriclab.model.dto.get.music.MusicPlayDto;
import com.lyriclab.lyriclab.model.dto.post.MusicCreationDTO;
import com.lyriclab.lyriclab.model.entity.Album;
import com.lyriclab.lyriclab.model.entity.Music;
import com.lyriclab.lyriclab.model.entity.Playlist;
import com.lyriclab.lyriclab.model.entity.user.User;
import com.lyriclab.lyriclab.repository.MusicRepository;
import com.lyriclab.lyriclab.service.user.UserService;
import com.lyriclab.lyriclab.util.AuthUserUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class MusicService {

    private final MusicRepository musicRepository;
    private final AlbumService albumService;
    private final PlaylistService playlistService;
    private final FileService fileService;

    private final AuthUserUtil authUtil;

    public MusicGetDto save(MusicCreationDTO dto, Long albumId) {
        try {
            Album album = albumService.findEntityById(albumId);
            Music music = new Music(dto, album);
            return musicRepository
                    .save(music).toDto();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public MusicGetDto save(MusicCreationDTO dto) {
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

    public List<MusicGetDto> findRecent() {
        User user = authUtil.getAuthenticatedUser();
        return user.getPlaylists().get(1)
                .toDto().getMusics();
    }


    public MusicPlayDto playMusic(Long musicId) {
        User user = authUtil.getAuthenticatedUser();

        Music music = findById(musicId);
        Playlist playlist = user.getPlaylists().get(1); // recently played playlist

        playlist.getMusics().remove(music);
        playlistService.save(playlist);

        playlist.getMusics().add(music);

        if (playlist.getMusics().size() > 12) {
            playlist.getMusics().remove(12);
        }

        playlistService.save(playlist);
        return music.toPlayDto();
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

    public MusicGetDto uploadFile(MultipartFile file, Long id) {
        try {
            Music music = findById(id);
            music.setFile(fileService.save(file));

            return musicRepository.save(music).toDto();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
