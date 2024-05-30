package com.lyriclab.lyriclab.model.entity;

import com.lyriclab.lyriclab.model.dto.get.music.MusicGetDto;
import com.lyriclab.lyriclab.model.dto.get.music.MusicPlayDto;
import com.lyriclab.lyriclab.model.dto.post.MusicCreationDTO;
import com.lyriclab.lyriclab.model.enums.Genre;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Entity
@Table(name = "tb_music")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String title;

    @Column(length = 80)
    private String artist;

    @ManyToOne(cascade =
            { CascadeType.PERSIST, CascadeType.MERGE })
    @ToString.Exclude
    private Album album;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @OneToOne(cascade =
            { CascadeType.PERSIST, CascadeType.REMOVE })
    private File file;

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

    public MusicPlayDto toPlayDto() {
        return new MusicPlayDto(this);
    }

}
