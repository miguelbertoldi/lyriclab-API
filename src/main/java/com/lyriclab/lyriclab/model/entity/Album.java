package com.lyriclab.lyriclab.model.entity;

import com.lyriclab.lyriclab.model.dto.post.AlbumCreationDTO;
import com.lyriclab.lyriclab.model.dto.post.MusicCreationDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_album")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String artist;

    @OneToMany(mappedBy = "album",
                cascade = CascadeType.ALL)
    private List<Music> musics;

    @OneToOne(cascade = CascadeType.REMOVE)
    private File cover;

    public Album(MusicCreationDTO dto) {
        this.title = dto.getTitle();
        this.artist = dto.getArtist();
        this.musics = new ArrayList<>();
    }

    public Album(AlbumCreationDTO dto) {
        BeanUtils.copyProperties(dto, this);
        this.musics = new ArrayList<>();
    }

    public void addMusic(Music music) {
        this.musics.add(music);
    }

}
