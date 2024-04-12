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
            return saveAlbumAndConvertToDto(
                    new Album(dto)
            );
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Album createMusicAlbum(MusicCreationDTO dto) {
        return new Album(dto);
    }

    public Album findByIdEntity(Long id) {
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
            Album album = findByIdEntity(id);
            album.setCover(fileService.save(multipartFile));
            return saveAlbumAndConvertToDto(album);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public AlbumGetDto saveAlbumAndConvertToDto(Album album) {
        return new AlbumGetDto(
                albumRepository.save(album)
        );
    }


}
