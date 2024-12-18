package com.ahmedferjani.music_metadata.services;

import com.ahmedferjani.music_metadata.client.POJO.searchAPI.HitList;
import com.ahmedferjani.music_metadata.client.POJO.songsAPI.MediaResult;
import com.ahmedferjani.music_metadata.domain.entities.Media;
import com.ahmedferjani.music_metadata.domain.entities.SearchTerm;
import com.ahmedferjani.music_metadata.domain.entities.Song;

import java.util.List;
import java.util.Optional;

public interface MusicDataIntegrationService {

    /**
     * Create and persist a searchTerm object from a given query (term).
     *
     * @param query the searchTerm object term
     * @return the saved searchTerm object
     */
    SearchTerm saveSearchTerm(String query);

    /**
     * Create and persist a Song object from given HitList and SearchTerm objects.
     *
     * @param hit        used to initialize the Song
     * @param searchTerm to be attached to the Song object
     * @return the saved Song object
     */
    Song saveSong(HitList hit, SearchTerm searchTerm);

    /**
     * Create and persist a Media object.
     *
     * @param mediaResult used to initialize the Media object
     * @param song        to be attached to the Media Object
     */
    void saveMedia(MediaResult mediaResult, Song song);

    /**
     * Fetches a song by its unique identifier.
     *
     * @param id the song identifier
     * @return the fetched song if exists
     */
    Optional<Song> findSongById(Long id);

    /**
     * Fetches the list of Media attached to a song.
     *
     * @param id the song identifier
     * @return list of Media attached to a song
     */
    List<Media> findMediasBySongId(Long id);
}
