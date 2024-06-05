package com.lyriclab.lyriclab.model.entity.user;

import com.lyriclab.lyriclab.model.dto.get.user.UserGetDto;
import com.lyriclab.lyriclab.model.dto.get.user.UserBasicInfoDto;
import com.lyriclab.lyriclab.model.dto.post.UserCreationDTO;
import com.lyriclab.lyriclab.model.entity.File;
import com.lyriclab.lyriclab.model.entity.Playlist;
import com.lyriclab.lyriclab.model.enums.PlaylistType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
            unique = true,
            length = 48)
    private String email;

    private String fullName;
    private String description;

    @Column(nullable = false)
    @ToString.Exclude
    private String password;

    @OneToMany(mappedBy = "owner",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Playlist> playlists;

    @OneToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    private File picture;

    @OneToOne(cascade = CascadeType.ALL)
    private UserDetailsEntity userDetails;

    public User(UserCreationDTO dto) {
        BeanUtils.copyProperties(dto, this);
        setPassword(dto.getPassword());
        this.playlists = List.of(
                new Playlist(this,
                        "Músicas curtidas",
                        "Aqui você pode ver todas as músicas que curtiu!",
                        PlaylistType.LIKED),
                new Playlist(this,
                        "Ouviu recentemente",
                        "Aqui você pode ver as útlimas músicas que você ouviu!",
                        PlaylistType.RECENT)
        ); //save default playlist -> liked musics, recently listened
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

    public UserBasicInfoDto getBasicInfo() {
        return new UserBasicInfoDto(this);
    }

    public void setPassword(String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

}
