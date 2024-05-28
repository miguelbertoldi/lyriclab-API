package com.lyriclab.lyriclab.controller;

import com.lyriclab.lyriclab.model.dto.post.MusicCreationDTO;
import com.lyriclab.lyriclab.model.dto.post.PlaylistCreationDTO;
import com.lyriclab.lyriclab.service.PlaylistService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlist")
@AllArgsConstructor
@CrossOrigin
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping
    public ResponseEntity<?> save(
            @RequestBody PlaylistCreationDTO dto) {
        try {
            return new ResponseEntity<>
                    (playlistService.save(dto),
                            HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>
                    (HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> findAllByUser() {
        try {
            return new ResponseEntity<>
                    (playlistService.findAllByUser(),
                            HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>
                    (HttpStatus.BAD_REQUEST);
        }
    }

}
