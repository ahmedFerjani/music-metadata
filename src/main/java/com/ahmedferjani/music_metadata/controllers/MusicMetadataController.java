package com.ahmedferjani.music_metadata.controllers;

import com.ahmedferjani.music_metadata.domain.dto.MediaDTO;
import com.ahmedferjani.music_metadata.domain.dto.SongDTO;
import com.ahmedferjani.music_metadata.domain.dto.SongIdsDTO;
import com.ahmedferjani.music_metadata.services.MusicMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/code-challenge")
public class MusicMetadataController {

    @Autowired
    private MusicMetadataService musicMetadataService;

    @PostMapping("/create-songs")
    public Mono<ResponseEntity<SongIdsDTO>> createSongs(@RequestParam String q) {

        Mono<SongIdsDTO> songIdsMono = musicMetadataService.storeSongs(q);

        return songIdsMono.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @GetMapping("/song/{id}")
    public ResponseEntity<SongDTO> getSongMetadata(@PathVariable Long id) {

        Optional<SongDTO> songDetails = musicMetadataService.getSongDetails(id);

        return songDetails
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/song/{id}/media")
    public ResponseEntity<List<MediaDTO>> getSongMedia(@PathVariable Long id) {

        List<MediaDTO> mediaList = musicMetadataService.getSongMedia(id);

        if (mediaList != null && !mediaList.isEmpty()) {
            return ResponseEntity.ok(mediaList);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
