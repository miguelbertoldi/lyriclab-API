package com.lyriclab.lyriclab.controller;

import com.lyriclab.lyriclab.model.dto.get.music.MusicResponseDto;
import com.lyriclab.lyriclab.model.dto.post.MusicPostDTO;
import com.lyriclab.lyriclab.model.entity.Music;
import com.lyriclab.lyriclab.model.enums.Genre;
import com.lyriclab.lyriclab.service.MusicService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @GetMapping("/search")
    public ResponseEntity<List<MusicResponseDto>> findByContainingTitle(@RequestParam String title){
        try {
            return new ResponseEntity<>
                    (musicService.findByContaining(title),
                            HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>
                    (HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/like/{musicId}")
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
            @RequestBody MusicPostDTO dto,
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
            @RequestBody MusicPostDTO dto) {
        try {
            return new ResponseEntity<>
                    (musicService.save(dto),
                            HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
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

    @PostMapping("/{id}/file")
    public ResponseEntity<?> uploadMusicFile(
            @RequestParam MultipartFile file,
            @PathVariable Long id) {
        try {
            return new ResponseEntity<>
                    (musicService.uploadFile(file, id),
                            HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/next/{currentMusicId}")
    public ResponseEntity<?> findNextMusicHistory(
            @PathVariable Long currentMusicId) {
        try {
            return new ResponseEntity<>(
                    musicService.findNextMusicHistory(currentMusicId),
                        HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST);
        }
    }

}
