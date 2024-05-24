package com.lyriclab.lyriclab.model.entity;

import com.lyriclab.lyriclab.model.dto.get.AlbumGetDto;
import com.lyriclab.lyriclab.model.dto.get.MusicGetDto;
import com.lyriclab.lyriclab.model.dto.post.MusicCreationDTO;
import com.lyriclab.lyriclab.model.enums.Genre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_music")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Column(length = 80)
    private String artist;

    @ManyToOne(cascade =
            { CascadeType.PERSIST, CascadeType.MERGE })
    private Album album;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private LocalDate releaseDate;
    private String lyrics;

    public Music(MusicCreationDTO dto, Album album) {
        BeanUtils.copyProperties(dto, this);
        this.album = album;
        album.addMusic(this);
    }

    public MusicGetDto toDto() {
        return new MusicGetDto(this);
    }

}
