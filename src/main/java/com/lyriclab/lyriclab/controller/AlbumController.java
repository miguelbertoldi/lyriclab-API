package com.lyriclab.lyriclab.controller;

import com.lyriclab.lyriclab.model.dto.get.AlbumResponseDto;
import com.lyriclab.lyriclab.model.dto.post.AlbumPostDTO;
import com.lyriclab.lyriclab.service.AlbumService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/album")
@AllArgsConstructor
@CrossOrigin
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping
    public ResponseEntity<List<AlbumResponseDto>> findAll() {
        return new ResponseEntity<>(
                albumService.findAll(),
                    HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody AlbumPostDTO dto) {
        try {
            return new ResponseEntity<>(
                    albumService.save(dto),
                        HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>
                    (HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> uploadFile(
            @PathVariable Long id,
            @RequestBody MultipartFile file
    ) {
        try {
            return new ResponseEntity<>
                    (albumService.uploadFile(id, file),
                            HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>
                    (HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> uploadFile(
            @PathVariable Long id
    ) {
        try {
            albumService.delete(id);
            return new ResponseEntity<>
                    (HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>
                    (HttpStatus.BAD_REQUEST);
        }
    }


}
