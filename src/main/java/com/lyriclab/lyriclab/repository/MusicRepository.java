package com.lyriclab.lyriclab.repository;

import com.lyriclab.lyriclab.model.entity.Music;
import com.lyriclab.lyriclab.model.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository
        extends JpaRepository<Music, Long> {

    @Query("SELECT m FROM Music m " +
            "   WHERE m.id != :currentId " +
            "   AND m.genre = :genre " +
            "   ORDER BY FUNCTION('RAND') " +
            "   LIMIT 1")
    Music findRandomByGenre(@Param("genre") Genre genre, // find a random music by this genre
                            @Param("currentId") Long currentId);

    List<Music> findAllByTitleContaining(String query);

}
