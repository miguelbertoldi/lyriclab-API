package com.lyriclab.lyriclab.controller;

import com.lyriclab.lyriclab.model.dto.post.PlaylistPostDTO;
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

    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            return new ResponseEntity<>(playlistService.getAll(),HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(
            @RequestBody PlaylistPostDTO dto) {
        try {
            return new ResponseEntity<>
                    (playlistService.save(dto),
                            HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
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

    @GetMapping("/logged/some")
    public ResponseEntity<?> findSomeUserPlaylists() {
        try {
            return new ResponseEntity<>
                    (playlistService.findSomeUserPlaylists(),
                            HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>
                    (HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/like/{playlistId}")
    public ResponseEntity<?> likeMusicHandler(
            @PathVariable Long playlistId) {
        try {
            playlistService.likePlaylistHandler(playlistId);
            return new ResponseEntity<>
                    (HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>
                    (HttpStatus.BAD_REQUEST);
        }
    }

}
