package com.ahmedferjani.music_metadata.services.impl;

import com.ahmedferjani.music_metadata.client.GeniusReactiveClient;
import com.ahmedferjani.music_metadata.domain.dto.MediaDTO;
import com.ahmedferjani.music_metadata.domain.dto.SongDTO;
import com.ahmedferjani.music_metadata.domain.entities.Media;
import com.ahmedferjani.music_metadata.domain.entities.SearchTerm;
import com.ahmedferjani.music_metadata.domain.entities.Song;
import com.ahmedferjani.music_metadata.mappers.MediaMapper;
import com.ahmedferjani.music_metadata.mappers.SongMapper;
import com.ahmedferjani.music_metadata.services.MediaProcessingService;
import com.ahmedferjani.music_metadata.services.MusicDataIntegrationService;
import com.ahmedferjani.music_metadata.services.MusicMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MusicMetadataServiceImpl implements MusicMetadataService {

    private final GeniusReactiveClient geniusReactiveClient;
    private final MusicDataIntegrationService musicDataIntegration;
    private final MediaProcessingService mediaProcessingService;
    private final MediaMapper mediaMapper;
    private final SongMapper songMapper;

    @Autowired
    public MusicMetadataServiceImpl(GeniusReactiveClient geniusReactiveClient, MusicDataIntegrationService musicDataIntegration, MediaProcessingService mediaProcessingService, MediaMapper mediaMapper, SongMapper songMapper) {
        this.geniusReactiveClient = geniusReactiveClient;
        this.musicDataIntegration = musicDataIntegration;
        this.mediaProcessingService = mediaProcessingService;
        this.mediaMapper = mediaMapper;
        this.songMapper = songMapper;
    }

    @Override
    public Optional<SongDTO> getSongDetails(Long id) {
        Optional<Song> song = musicDataIntegration.findSongById(id);

        return song.map(songMapper::toDTO);
    }

    @Override
    public List<MediaDTO> getSongMedia(Long id) {
        List<Media> mediaList = musicDataIntegration.findMediasBySongId(id);

        return mediaList.stream().map(mediaMapper::toDTO).toList();
    }

    @Override
    public Mono<List<Long>> storeSongs(String query) {
        SearchTerm searchTerm = musicDataIntegration.saveSearchTerm(query);
        List<Long> savedSongIds = new ArrayList<>();

        // Performing an API call to fetch 10 songs (in maximum) with detailed info
        return geniusReactiveClient.searchSongs(query)
                .flatMap(response -> {
                    response.getResponse().getHits().stream()
                            .limit(10)
                            .forEach(hit -> {
                                Song song = musicDataIntegration.saveSong(hit, searchTerm);
                                savedSongIds.add(song.getId());

                                mediaProcessingService.processMedia(song);
                            });

                    return Mono.just(savedSongIds);
                }).doOnError(error -> {
                    System.err.println("[MusicMetadataService] Error: " + error.getMessage());
                });
    }
}
