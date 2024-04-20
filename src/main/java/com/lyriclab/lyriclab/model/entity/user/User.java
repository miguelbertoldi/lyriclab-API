package com.lyriclab.lyriclab.model.entity.user;

import com.lyriclab.lyriclab.model.dto.get.user.UserGetDto;
import com.lyriclab.lyriclab.model.dto.post.UserCreationDTO;
import com.lyriclab.lyriclab.model.entity.File;
import com.lyriclab.lyriclab.model.entity.Playlist;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 16,
            nullable = false,
            unique = true)
    private String username;

    @Column(nullable = false,
            length = 48)
    private String email;

    private String fullName;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "owner",
            cascade = CascadeType.PERSIST)
    private List<Playlist> playlists;

    @OneToOne
    private File picture;

    @OneToOne(cascade = CascadeType.ALL)
    private UserDetailsEntity userDetails;

    public User(UserCreationDTO dto) {
        BeanUtils.copyProperties(dto, this);
        this.playlists = List.of(new Playlist(this)); //save default playlist -> liked musics
        setUserDetails();
    }

    public void setUserDetails() {
        this.userDetails =
                UserDetailsEntity.builder()
                        .user(this)
                        .build();
    }

    public UserGetDto toDto() {
        return new UserGetDto(this);
    }

    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }
}
