package com.ahmedferjani.music_metadata.controllers;

import com.ahmedferjani.music_metadata.domain.dto.MediaDTO;
import com.ahmedferjani.music_metadata.domain.dto.SongDTO;
import com.ahmedferjani.music_metadata.services.MusicMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Mono<List<Long>> createSongs(@RequestParam String q) {

        return musicMetadataService.storeSongs(q);
    }

    @GetMapping("/song/{id}")
    public Optional<SongDTO> getSongMetadata(@PathVariable Long id) {

        return musicMetadataService.getSongDetails(id);
    }

    @GetMapping("/song/{id}/media")
    public List<MediaDTO> getSongMedia(@PathVariable Long id) {

        return musicMetadataService.getSongMedia(id);
    }
}
