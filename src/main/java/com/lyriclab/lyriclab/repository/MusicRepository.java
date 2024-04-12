package com.lyriclab.lyriclab.repository;

import com.lyriclab.lyriclab.model.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository
        extends JpaRepository<Music, Long> {
}
