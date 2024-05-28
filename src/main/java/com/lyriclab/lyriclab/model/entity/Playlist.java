package com.lyriclab.lyriclab.model.entity;

import com.lyriclab.lyriclab.model.dto.get.PlaylistGetDto;
import com.lyriclab.lyriclab.model.dto.post.PlaylistCreationDTO;
import com.lyriclab.lyriclab.model.entity.user.User;
import com.lyriclab.lyriclab.model.enums.PlaylistType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_playlist")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(length = 32)
    private String title;

    @Column(length = 150)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Music> musics;

    @ManyToOne
    private User owner;

    @Enumerated(EnumType.STRING)
    private PlaylistType type;

    private Boolean mandatory;

    public Playlist( //default playlists -> liked musics, recently listened
            User user, String title,
            String description, PlaylistType type
    ) {
        this.title = title;
        this.description = description;
        this.musics = new ArrayList<>();
        this.mandatory = true;
        this.owner = user;
        this.type = type;
    }

    public Playlist(PlaylistCreationDTO dto, User user) {
        BeanUtils.copyProperties(dto, this);
        this.owner = user;
        this.mandatory = false;
        this.musics = new ArrayList<>();
        this.type = PlaylistType.SIMPLE;
    }

    public PlaylistGetDto toDto() {
        return new PlaylistGetDto(this);
    }

}
