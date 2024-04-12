package com.lyriclab.lyriclab.repository;

import com.lyriclab.lyriclab.model.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository
        extends JpaRepository<Playlist, Long> {
}
