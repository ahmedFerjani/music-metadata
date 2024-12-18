package com.ahmedferjani.music_metadata.services;


import com.ahmedferjani.music_metadata.domain.dto.MediaDTO;
import com.ahmedferjani.music_metadata.domain.dto.SongDTO;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface MusicMetadataService {

    /**
     * Fetches the details of a song by its unique identifier.
     *
     * @param id the song identifier
     * @return the fetched song details if exists.
     */
    Optional<SongDTO> getSongDetails(Long id);

    /**
     * Fetches the song's media list.
     *
     * @param id the song identifier
     * @return the fetched song's media list
     */
    List<MediaDTO> getSongMedia(Long id);

    /**
     * Stores songs related to the given search query.
     * This method performs the following:
     * - Saves the search term to the database.
     * - Fetches song results from an external API based on the search query.
     * - Stores up to 10 songs in the database, including their metadata, associated media and cover photo.
     *
     * @param query the search term used to fetch songs
     * @return a {@link Mono} emitting a {@link List} of song IDs that were successfully stored
     */
    Mono<List<Long>> storeSongs(String query);
}
