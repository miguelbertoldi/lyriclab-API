package com.lyriclab.lyriclab.service;

import com.lyriclab.lyriclab.model.dto.get.MusicGetDto;
import com.lyriclab.lyriclab.model.dto.post.MusicCreationDTO;
import com.lyriclab.lyriclab.model.entity.Album;
import com.lyriclab.lyriclab.model.entity.Music;
import com.lyriclab.lyriclab.repository.MusicRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MusicService {

    private final MusicRepository musicRepository;
    private final AlbumService albumService;

    public MusicGetDto save(MusicCreationDTO dto, Long albumId) {
        try {
            Album album = albumService.findEntityById(albumId);
            Music music = new Music(dto, album);
            musicRepository.save(music);
            return music.toDto();

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


}
