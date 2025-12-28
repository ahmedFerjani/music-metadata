package com.ahmedferjani.music_metadata.services.impl;

import com.ahmedferjani.music_metadata.client.GeniusReactiveClient;
import com.ahmedferjani.music_metadata.client.POJO.songsAPI.MediaResult;
import com.ahmedferjani.music_metadata.domain.entities.Song;
import com.ahmedferjani.music_metadata.services.MediaProcessingService;
import com.ahmedferjani.music_metadata.services.MusicDataIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MediaProcessingServiceImpl implements MediaProcessingService {

    public static final List<String> ALLOWED_PLATFORMS = List.of("youtube", "spotify");
    private final GeniusReactiveClient geniusReactiveClient;
    private final MusicDataIntegrationService musicDataIntegration;

    @Autowired
    public MediaProcessingServiceImpl(GeniusReactiveClient geniusReactiveClient,
            MusicDataIntegrationService musicDataIntegration) {
        this.geniusReactiveClient = geniusReactiveClient;
        this.musicDataIntegration = musicDataIntegration;
    }

    public void processMedia(Song song) {
        // Retrieving the media of a song by performing another API call
        geniusReactiveClient.getSongDetails(song.getId())
                .flatMap(songDetails -> {
                    songDetails.getResponse().getSong().getMedia()
                            .stream()
                            .filter(this::checkMediaResult)
                            .forEach(mediaResult -> {
                                musicDataIntegration.saveMedia(mediaResult, song);
                            });

                    return Mono.empty();
                }).subscribe();
    }

    /**
     * Define criteria of media acceptance
     *
     * @param mediaResult processed data
     * @return check result indicating the acceptance of defined criteria
     */
    private boolean checkMediaResult(MediaResult mediaResult) {
        String platform = mediaResult.getProvider();

        return ALLOWED_PLATFORMS.contains(platform);
    }
}
