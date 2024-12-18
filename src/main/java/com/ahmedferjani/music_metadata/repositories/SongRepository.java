package com.ahmedferjani.music_metadata.repositories;

import com.ahmedferjani.music_metadata.domain.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}
