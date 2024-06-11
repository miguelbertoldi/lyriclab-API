package com.lyriclab.lyriclab.service;

import com.lyriclab.lyriclab.model.dto.get.AlbumResponseDto;
import com.lyriclab.lyriclab.model.dto.post.AlbumPostDTO;
import com.lyriclab.lyriclab.model.dto.post.MusicPostDTO;
import com.lyriclab.lyriclab.model.entity.Album;
import com.lyriclab.lyriclab.model.entity.user.Artist;
import com.lyriclab.lyriclab.model.entity.user.User;
import com.lyriclab.lyriclab.repository.AlbumRepository;
import com.lyriclab.lyriclab.service.user.UserService;
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
    private final UserService userService;

    public AlbumResponseDto save(AlbumPostDTO dto) {
        try {
            User artist = userService.findArtist(dto.getArtistId());
            Album album = new Album(dto, artist);
            saveDefaultCover(album);
            return albumRepository.save(album).toDto();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Album createMusicAlbum(MusicPostDTO dto) {
        try {
            User artist = userService.findArtist(dto.getArtistId());
            Album album = new Album(dto, artist);
            saveDefaultCover(album);
            return album;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    public List<AlbumResponseDto> findAll() {
        return findAllEntity()
                .stream()
                .map(AlbumResponseDto::new)
                .toList();
    }

    public AlbumResponseDto uploadFile(
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
