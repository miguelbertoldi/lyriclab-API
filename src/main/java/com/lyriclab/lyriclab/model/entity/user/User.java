    package com.lyriclab.lyriclab.model.entity.user;

    import com.lyriclab.lyriclab.model.dto.get.user.UserResponseDto;
    import com.lyriclab.lyriclab.model.dto.get.user.UserBasicInfoDto;
    import com.lyriclab.lyriclab.model.dto.post.UserPostDTO;
    import com.lyriclab.lyriclab.model.entity.File;
    import com.lyriclab.lyriclab.model.entity.Playlist;
    import com.lyriclab.lyriclab.model.enums.PlaylistType;
    import com.lyriclab.lyriclab.model.enums.UserKind;
    import com.lyriclab.lyriclab.model.interfaces.IResponseConversor;
    import jakarta.persistence.*;
    import lombok.*;
    import org.springframework.beans.BeanUtils;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;

    import java.util.List;

    @Entity
    @Table(name = "tb_user")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Inheritance(strategy = InheritanceType.JOINED)
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    public class User implements IResponseConversor<UserResponseDto> {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @EqualsAndHashCode.Include
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

        @Enumerated(EnumType.STRING)
        private UserKind userKind;

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

        public User(UserPostDTO dto) {
            BeanUtils.copyProperties(dto, this);
            setPassword(dto.getPassword());
            this.userKind = UserKind.DEFAULT;
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
            buildUserDetails();
        }

        public User(User user) {
            BeanUtils.copyProperties(user, this);
        }

        private void buildUserDetails() {
            this.userDetails =
                    UserDetailsEntity.builder()
                            .user(this)
                            .build();
        }

        public UserResponseDto toDto() {
            return new UserResponseDto(this);
        }

        public UserBasicInfoDto getBasicInfo() {
            return new UserBasicInfoDto(this);
        }

        public void setPassword(String password) {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            this.password = encoder.encode(password);
        }

    }
