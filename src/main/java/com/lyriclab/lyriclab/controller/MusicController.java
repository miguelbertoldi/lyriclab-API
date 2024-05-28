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

    @GetMapping("/play/{musicId}")
    public ResponseEntity<?> playMusic(
            @PathVariable Long musicId) {
        try {
            return new ResponseEntity<>
                    (musicService.playMusic(musicId),
                            HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>
                    (HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/like/{musicId}")
    public ResponseEntity<?> likeMusicHandler(
            @PathVariable Long musicId) {
        try {
            musicService.likeMusicHandler(musicId);
            return new ResponseEntity<>
                    (HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>
                    (HttpStatus.BAD_REQUEST);
        }
    }

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

    @GetMapping("/recent")
    public ResponseEntity<?> findRecent() {
        try {
            return new ResponseEntity<>
                    (musicService.findRecent(),
                            HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST);
        }
    }

}
