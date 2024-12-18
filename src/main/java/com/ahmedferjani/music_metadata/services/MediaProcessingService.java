package com.ahmedferjani.music_metadata.services;

import com.ahmedferjani.music_metadata.domain.entities.Song;

public interface MediaProcessingService {

    /**
     * Process the media for a given song:
     * retrieve the list of media data that is compatible to the set of criteria and save it
     *
     * @param song input from which extract the media related to it
     */
    void processMedia(Song song);
}
