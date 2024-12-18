package com.ahmedferjani.music_metadata.services;

import com.ahmedferjani.music_metadata.client.GeniusReactiveClient;
import com.ahmedferjani.music_metadata.client.POJO.songsAPI.MediaResult;
import com.ahmedferjani.music_metadata.domain.dto.MediaDTO;
import com.ahmedferjani.music_metadata.domain.dto.SongDTO;
import com.ahmedferjani.music_metadata.domain.entities.Media;
import com.ahmedferjani.music_metadata.domain.entities.SearchTerm;
import com.ahmedferjani.music_metadata.domain.entities.Song;
import com.ahmedferjani.music_metadata.mappers.MediaMapper;
import com.ahmedferjani.music_metadata.mappers.SongMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MusicMetadataServiceImpl implements MusicMetadataService {

    public static final List<String> ALLOWED_PLATFORMS = List.of("youtube", "spotify");

    private final GeniusReactiveClient geniusReactiveClient;
    private final MusicDataIntegrationService musicDataIntegration;
    private final MediaMapper mediaMapper;
    private final SongMapper songMapper;

    @Autowired
    public MusicMetadataServiceImpl(GeniusReactiveClient geniusReactiveClient, MusicDataIntegrationService musicDataIntegration, MediaMapper mediaMapper, SongMapper songMapper) {
        this.geniusReactiveClient = geniusReactiveClient;
        this.musicDataIntegration = musicDataIntegration;
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

                                this.processMedia(song);
                            });
                    return Mono.just(savedSongIds);
                });
    }

    private void processMedia(Song song) {
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
