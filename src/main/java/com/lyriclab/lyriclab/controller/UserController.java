package com.lyriclab.lyriclab.controller;

import com.lyriclab.lyriclab.model.dto.post.UserCreationDTO;
import com.lyriclab.lyriclab.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                    (userService.register(dto),
                        HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>
                    (HttpStatus.CONFLICT);
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
                    (HttpStatus.NOT_FOUND);
        }
    }

}
