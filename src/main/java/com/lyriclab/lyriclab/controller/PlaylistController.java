package com.lyriclab.lyriclab.controller;

import com.lyriclab.lyriclab.service.PlaylistService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playlist")
@AllArgsConstructor
@CrossOrigin
public class PlaylistController {

    private final PlaylistService playlistService;

}
