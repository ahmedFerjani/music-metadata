package com.ahmedferjani.music_metadata.services.impl;

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
import com.ahmedferjani.music_metadata.services.FileManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MusicDataIntegrationServiceImplTest {

    private SongRepository songRepository;
    private MediaRepository mediaRepository;
    private SearchTermRepository searchTermRepository;
    private FileManagerService fileManagerService;
    private MusicDataIntegrationServiceImpl musicDataIntegrationService;

    @BeforeEach
    void setUp() {
        songRepository = mock(SongRepository.class);
        mediaRepository = mock(MediaRepository.class);
        searchTermRepository = mock(SearchTermRepository.class);
        fileManagerService = mock(FileManagerService.class);

        musicDataIntegrationService = new MusicDataIntegrationServiceImpl(
                songRepository,
                mediaRepository,
                searchTermRepository,
                fileManagerService);
    }

    @Test
    void saveSearchTerm_shouldSaveAndReturnSearchTerm() {
        String query = "Test Song";
        SearchTerm result = musicDataIntegrationService.saveSearchTerm(query);

        assertNotNull(result);
        assertEquals(query, result.getTerm());
        verify(searchTermRepository).save(result);
    }

    @Test
    void saveSong_shouldSaveSongWithImage() throws IOException, URISyntaxException {
        // Prepare mocks
        HitResult hitResult = new HitResult();
        hitResult.setId(1L);
        hitResult.setTitle("Song Title");
        hitResult.setPrimaryArtistNames("Artist Name");
        hitResult.setHeaderImageThumbnailUrl("http://image.url/image.jpg");
        ReleaseDateComponents releaseDate = new ReleaseDateComponents();
        releaseDate.setYear(2020);
        releaseDate.setMonth(5);
        releaseDate.setDay(10);
        hitResult.setReleaseDateComponents(releaseDate);

        HitList hitList = new HitList();
        hitList.setResult(hitResult);

        SearchTerm searchTerm = new SearchTerm();

        when(fileManagerService.saveImageFromUrl(anyString(), anyString(), anyString()))
                .thenReturn("savedImage.jpg");

        Song song = musicDataIntegrationService.saveSong(hitList, searchTerm);

        assertNotNull(song);
        assertEquals(1L, song.getId());
        assertEquals("Song Title", song.getTitle());
        assertEquals("Artist Name", song.getPrimaryArtistNames());
        assertEquals("savedImage.jpg", song.getCoverPath());
        assertEquals(LocalDate.of(2020, 5, 10), song.getReleaseDate());
        assertEquals(searchTerm, song.getSearchTerm());

        verify(songRepository).save(song);
    }

    @Test
    void saveSong_shouldHandleIOExceptionGracefully() throws IOException, URISyntaxException {
        HitResult hitResult = new HitResult();
        hitResult.setId(2L);
        hitResult.setTitle("Song2");
        hitResult.setPrimaryArtistNames("Artist2");
        hitResult.setHeaderImageThumbnailUrl("http://invalid-url/image.jpg");

        HitList hitList = new HitList();
        hitList.setResult(hitResult);

        SearchTerm searchTerm = new SearchTerm();

        // Simulate exception in FileManagerService
        when(fileManagerService.saveImageFromUrl(anyString(), anyString(), anyString()))
                .thenThrow(new IOException("Failed"));

        Song song = musicDataIntegrationService.saveSong(hitList, searchTerm);

        assertNotNull(song);
        assertNull(song.getCoverPath()); // image failed, coverPath should be null
        verify(songRepository).save(song);
    }

    @Test
    void saveMedia_shouldSaveMedia() {
        Song song = new Song();
        MediaResult mediaResult = new MediaResult();
        mediaResult.setProvider("youtube");
        mediaResult.setUrl("http://youtube.com/video");

        musicDataIntegrationService.saveMedia(mediaResult, song);

        ArgumentCaptor<Media> captor = ArgumentCaptor.forClass(Media.class);
        verify(mediaRepository).save(captor.capture());

        Media savedMedia = captor.getValue();
        assertEquals("youtube", savedMedia.getPlatform());
        assertEquals("http://youtube.com/video", savedMedia.getUrl());
        assertEquals(song, savedMedia.getSong());
    }

    @Test
    void findSongById_shouldReturnSongOptional() {
        Song song = new Song();
        song.setId(1L);
        when(songRepository.findById(1L)).thenReturn(Optional.of(song));

        Optional<Song> result = musicDataIntegrationService.findSongById(1L);

        assertTrue(result.isPresent());
        assertEquals(song, result.get());
    }

    @Test
    void findMediasBySongId_shouldReturnMediaList() {
        Song song = new Song();
        song.setId(1L);

        Media media1 = new Media();
        media1.setUrl("url1");
        media1.setPlatform("spotify");
        media1.setSong(song);

        Media media2 = new Media();
        media2.setUrl("url2");
        media2.setPlatform("youtube");
        media2.setSong(song);

        when(mediaRepository.findBySongId(1L)).thenReturn(List.of(media1, media2));

        List<Media> result = musicDataIntegrationService.findMediasBySongId(1L);

        assertEquals(2, result.size());
        assertEquals(media1, result.get(0));
        assertEquals(media2, result.get(1));
    }
}
