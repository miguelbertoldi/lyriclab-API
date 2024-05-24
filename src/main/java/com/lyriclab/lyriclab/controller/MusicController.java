package com.lyriclab.lyriclab.controller;

import com.lyriclab.lyriclab.model.dto.post.MusicCreationDTO;
import com.lyriclab.lyriclab.service.MusicService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/music")
@AllArgsConstructor
@CrossOrigin
public class MusicController {

    private final MusicService musicService;

    @PostMapping("/{albumId}")
    public ResponseEntity<?> save(
            @RequestBody MusicCreationDTO dto,
            @PathVariable Long albumId) {
        try {
            return new ResponseEntity<>
                    (musicService.save(dto, albumId),
                            HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>
                    (HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(
            @RequestBody MusicCreationDTO dto) {
        try {
            return new ResponseEntity<>
                    (musicService.save(dto),
                            HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>
                    (HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            return new ResponseEntity<>
                    (musicService.findAll(),
                            HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST);
        }
    }

}
