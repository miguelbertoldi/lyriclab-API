package com.lyriclab.lyriclab.model.entity.user;

import com.lyriclab.lyriclab.model.dto.post.ArtistPostDTO;
import com.lyriclab.lyriclab.model.entity.Album;
import com.lyriclab.lyriclab.model.enums.UserKind;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_artist")
@PrimaryKeyJoinColumn(name = "user_id")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Artist extends User {

    private String stageName;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Album> albums;

    public Artist(User user, ArtistPostDTO dto) {
        super(user);
        this.stageName = dto.getStageName();
        this.setUserKind(UserKind.ARTIST);
        this.albums = new ArrayList<>();
    }

}


