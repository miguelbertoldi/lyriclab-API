package com.lyriclab.lyriclab.model.entity;

import com.lyriclab.lyriclab.model.dto.get.AlbumResponseDto;
import com.lyriclab.lyriclab.model.dto.post.AlbumPostDTO;
import com.lyriclab.lyriclab.model.dto.post.MusicPostDTO;
import com.lyriclab.lyriclab.model.entity.user.User;
import com.lyriclab.lyriclab.model.interfaces.IResponseConversor;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_album")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Album implements IResponseConversor<AlbumResponseDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String title;

    @ManyToOne(cascade =
            { CascadeType.PERSIST, CascadeType.MERGE })
    private User artist;

    @OneToMany(mappedBy = "album",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    private List<Music> musics;

    @OneToOne(cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private File cover;

    public Album(MusicPostDTO dto, User artist) {
        this.title = dto.getTitle();
        this.artist = artist;
        this.musics = new ArrayList<>();
    }

    public AlbumResponseDto toDto() {
        return new AlbumResponseDto(this);
    }

    public Album(AlbumPostDTO dto, User artist) {
        BeanUtils.copyProperties(dto, this);
        this.artist = artist;
        this.musics = new ArrayList<>();
    }

    public void addMusic(Music music) {
        this.musics.add(music);
    }

}
