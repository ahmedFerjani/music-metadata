package com.ahmedferjani.music_metadata.repositories;

import com.ahmedferjani.music_metadata.domain.entities.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {

    /**
     * Retrieves a list of {@link Media} entities associated with the specified song.
     *
     * @param songId the ID of the song
     * @return a {@link List} of {@link Media} entities associated with the given song ID
     */
    List<Media> findBySongId(Long songId);
}
