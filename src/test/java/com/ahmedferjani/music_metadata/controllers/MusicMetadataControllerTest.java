package com.ahmedferjani.music_metadata.controllers;

import com.ahmedferjani.music_metadata.domain.dto.MediaDTO;
import com.ahmedferjani.music_metadata.domain.dto.SongDTO;
import com.ahmedferjani.music_metadata.domain.dto.SongIdsDTO;
import com.ahmedferjani.music_metadata.services.MusicMetadataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MusicMetadataControllerTest {

    @Mock
    private MusicMetadataService musicMetadataService;

    @InjectMocks
    private MusicMetadataController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSongs_shouldReturnOk_whenServiceReturnsData() {
        String query = "test song";
        SongIdsDTO songIds = new SongIdsDTO(Arrays.asList(1L, 2L, 3L));
        when(musicMetadataService.storeSongs(query)).thenReturn(Mono.just(songIds));

        Mono<ResponseEntity<SongIdsDTO>> responseMono = controller.createSongs(query);

        ResponseEntity<SongIdsDTO> response = responseMono.block();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(songIds, response.getBody());
        verify(musicMetadataService, times(1)).storeSongs(query);
    }

    @Test
    void createSongs_shouldReturnNoContent_whenServiceReturnsEmpty() {
        String query = "test song";
        when(musicMetadataService.storeSongs(query)).thenReturn(Mono.empty());

        Mono<ResponseEntity<SongIdsDTO>> responseMono = controller.createSongs(query);

        ResponseEntity<SongIdsDTO> response = responseMono.block();
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(musicMetadataService, times(1)).storeSongs(query);
    }

    @Test
    void getSongMetadata_shouldReturnOk_whenSongExists() {
        Long id = 1L;
        SongDTO songDTO = new SongDTO(); // fill with sample data if needed
        when(musicMetadataService.getSongDetails(id)).thenReturn(Optional.of(songDTO));

        ResponseEntity<SongDTO> response = controller.getSongMetadata(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(songDTO, response.getBody());
        verify(musicMetadataService, times(1)).getSongDetails(id);
    }

    @Test
    void getSongMetadata_shouldReturnNotFound_whenSongDoesNotExist() {
        Long id = 1L;
        when(musicMetadataService.getSongDetails(id)).thenReturn(Optional.empty());

        ResponseEntity<SongDTO> response = controller.getSongMetadata(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(musicMetadataService, times(1)).getSongDetails(id);
    }

    @Test
    void getSongMedia_shouldReturnOk_whenMediaExists() {
        Long id = 1L;
        List<MediaDTO> mediaList = Arrays.asList(new MediaDTO());
        when(musicMetadataService.getSongMedia(id)).thenReturn(mediaList);

        ResponseEntity<List<MediaDTO>> response = controller.getSongMedia(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mediaList, response.getBody());
        verify(musicMetadataService, times(1)).getSongMedia(id);
    }

    @Test
    void getSongMedia_shouldReturnNoContent_whenMediaIsEmpty() {
        Long id = 1L;
        when(musicMetadataService.getSongMedia(id)).thenReturn(Arrays.asList());

        ResponseEntity<List<MediaDTO>> response = controller.getSongMedia(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(musicMetadataService, times(1)).getSongMedia(id);
    }

    @Test
    void getSongMedia_shouldReturnNoContent_whenMediaIsNull() {
        Long id = 1L;
        when(musicMetadataService.getSongMedia(id)).thenReturn(null);

        ResponseEntity<List<MediaDTO>> response = controller.getSongMedia(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(musicMetadataService, times(1)).getSongMedia(id);
    }
}
