package com.lyriclab.lyriclab.controller;

import com.lyriclab.lyriclab.model.dto.get.user.UserEditDto;
import com.lyriclab.lyriclab.model.dto.post.UserCreationDTO;
import com.lyriclab.lyriclab.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody UserCreationDTO dto) {
        try {
            return new ResponseEntity<>
                    (dto,
                        HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>
                    (HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(
            @PathVariable Long id) {
        try {
            return new ResponseEntity<>
                    (userService.findById(id),
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
                    (userService.uploadFile(id, file),
                            HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>
                    (HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/logged")
    public ResponseEntity<?> findLoggedUser() {
        try {
            return new ResponseEntity<>
                    (userService.findLoggedUser(),
                            HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>
                    (HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/logged/complete")
    public ResponseEntity<?> findLoggedUserComplete() {
        try {
            return new ResponseEntity<>
                    (userService.findLoggedUserComplete(),
                            HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>
                    (HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/logged/musics")
    public ResponseEntity<?> findLikedLoggedMusics() {
        try {
            return new ResponseEntity<>
                    (userService.findLikedLoggedMusics(),
                            HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>
                    (HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Boolean> existsByEmail(
            @RequestBody String email) {
        return new ResponseEntity<>(
                userService.existsByEmail(email),
                    HttpStatus.OK);
    }

    @PutMapping
    public void editUser(@RequestBody UserEditDto userEditDto){
        userService.editUser(userEditDto);
    }

}
