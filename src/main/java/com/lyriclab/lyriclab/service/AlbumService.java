package com.lyriclab.lyriclab.service;

import com.lyriclab.lyriclab.model.dto.get.AlbumGetDto;
import com.lyriclab.lyriclab.model.dto.post.AlbumCreationDTO;
import com.lyriclab.lyriclab.model.dto.post.MusicCreationDTO;
import com.lyriclab.lyriclab.model.entity.Album;
import com.lyriclab.lyriclab.model.entity.File;
import com.lyriclab.lyriclab.model.entity.Music;
import com.lyriclab.lyriclab.repository.AlbumRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final FileService fileService;

    public AlbumGetDto save(AlbumCreationDTO dto) {
        try {
            Album album = new Album(dto);
            saveDefaultCover(album);
            return albumRepository.save(album).toDto();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Album createMusicAlbum(MusicCreationDTO dto) {
        Album album = new Album(dto);
        saveDefaultCover(album);
        return album;
    }

    private void saveDefaultCover(Album album) {
        album.setCover(
                fileService.loadImageAsFile(
                        "src/main/resources/images/default-playlist.png"
                )
        );
    }

    public Album findEntityById(Long id) {
        return albumRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Album> findAllEntity() {
        return albumRepository.findAll();
    }

    public List<AlbumGetDto> findAll() {
        return findAllEntity()
                .stream()
                .map(AlbumGetDto::new)
                .toList();
    }

    public AlbumGetDto uploadFile(
            Long id, MultipartFile multipartFile) {
        try {
            Album album = findEntityById(id);
            album.setCover(fileService.save(multipartFile));

            return albumRepository.save(album).toDto();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public void delete(Long id) {
        if (!albumRepository.existsById(id)) {
            throw new RuntimeException("Album doesn't exists");
        }
        albumRepository.deleteById(id);
    }
}
