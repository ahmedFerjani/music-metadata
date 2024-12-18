package com.ahmedferjani.music_metadata.services;

import com.ahmedferjani.music_metadata.client.POJO.searchAPI.HitList;
import com.ahmedferjani.music_metadata.client.POJO.searchAPI.HitResult;
import com.ahmedferjani.music_metadata.client.POJO.searchAPI.ReleaseDateComponents;
import com.ahmedferjani.music_metadata.client.POJO.songsAPI.MediaResult;
import com.ahmedferjani.music_metadata.domain.entities.Media;
import com.ahmedferjani.music_metadata.domain.entities.SearchTerm;
import com.ahmedferjani.music_metadata.domain.entities.Song;
import com.ahmedferjani.music_metadata.repositories.MediaRepository;
import com.ahmedferjani.music_metadata.repositories.SearchTermRepository;
import com.ahmedferjani.music_metadata.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MusicDataIntegrationServiceImpl implements MusicDataIntegrationService {

    private final SongRepository songRepository;
    private final MediaRepository mediaRepository;
    private final SearchTermRepository searchTermRepository;
    private final FileManagerService fileManagerService;

    @Autowired
    public MusicDataIntegrationServiceImpl(SongRepository songRepository,
                                           MediaRepository mediaRepository,
                                           SearchTermRepository searchTermRepository,
                                           FileManagerService fileManagerService) {
        this.songRepository = songRepository;
        this.mediaRepository = mediaRepository;
        this.searchTermRepository = searchTermRepository;
        this.fileManagerService = fileManagerService;
    }

    public SearchTerm saveSearchTerm(String query) {
        // instantiate and save the search term
        SearchTerm searchTerm = new SearchTerm();
        searchTerm.setTerm(query);
        searchTermRepository.save(searchTerm);
        return searchTerm;
    }

    public Song saveSong(HitList hit, SearchTerm searchTerm) {
        HitResult hitResult = hit.getResult();
        Long songId = hitResult.getId();

        // Storing the image and getting its full name
        String savedImagePath = null;
        try {
            savedImagePath = fileManagerService.saveImageFromUrl(hitResult.getHeaderImageUrl(), String.valueOf(songId), "jpg");
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        Song song = new Song();
        song.setId(songId);
        song.setTitle(hitResult.getTitle());
        song.setPrimaryArtistNames(hitResult.getPrimaryArtistNames());
        song.setCoverPath(savedImagePath);

        // creating the release date from provided year, month and day values
        ReleaseDateComponents releaseDateComponents = hitResult.getReleaseDateComponents();
        LocalDate releaseDate = LocalDate.of(releaseDateComponents.getYear(), releaseDateComponents.getMonth(), releaseDateComponents.getDay());
        song.setReleaseDate(releaseDate);

        song.setSearchTerm(searchTerm);

        songRepository.save(song);

        return song;
    }

    public void saveMedia(MediaResult mediaResult, Song song) {
        Media media = new Media();
        media.setUrl(mediaResult.getUrl());
        media.setPlatform(mediaResult.getProvider());
        media.setSong(song);

        mediaRepository.save(media);
    }

    public Optional<Song> findSongById(Long id) {

        return songRepository.findById(id);
    }

    public List<Media> findMediasBySongId(Long id) {

        return mediaRepository.findBySongId(id);
    }
}
