package com.lyriclab.lyriclab.controller;

import com.lyriclab.lyriclab.model.dto.get.AlbumGetDto;
import com.lyriclab.lyriclab.model.dto.post.AlbumCreationDTO;
import com.lyriclab.lyriclab.model.entity.Album;
import com.lyriclab.lyriclab.service.AlbumService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
@AllArgsConstructor
@CrossOrigin
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping
    public ResponseEntity<List<AlbumGetDto>> findAll() {
        return new ResponseEntity<>(
                albumService.findAll(),
                    HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody AlbumCreationDTO dto) {
        try {
            return new ResponseEntity<>(
                    albumService.save(dto),
                        HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(
                    HttpStatus.CONFLICT);
        }
    }


}