package com.lyriclab.lyriclab.repository;

import com.lyriclab.lyriclab.model.entity.user.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository
        extends JpaRepository<Artist, Long> {
}
