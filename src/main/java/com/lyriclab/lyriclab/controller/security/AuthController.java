package com.lyriclab.lyriclab.controller.security;

import com.lyriclab.lyriclab.service.user.AuthService;
import com.lyriclab.lyriclab.model.dto.get.user.UserLoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@AllArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody UserLoginDto dto,
            HttpServletRequest req,
            HttpServletResponse res
    ) {
        try {
            return new ResponseEntity<>
                    (authService.login(dto, req, res),
                            HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>
                    (HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            HttpServletRequest req,
            HttpServletResponse res
    ) {
        try {
            authService.logout(req, res);
            return new ResponseEntity<>
                    (HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>
                    (HttpStatus.CONFLICT);
        }
    }

}
