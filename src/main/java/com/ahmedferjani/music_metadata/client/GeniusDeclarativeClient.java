package com.ahmedferjani.music_metadata.client;

import com.ahmedferjani.music_metadata.client.POJO.searchAPI.SearchResponse;
import com.ahmedferjani.music_metadata.client.POJO.songsAPI.SongsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "geniusClient", url = "https://api.genius.com")
public interface GeniusDeclarativeClient {

    @GetMapping("/search")
    SearchResponse searchSongs(@RequestParam("q") String query, @RequestHeader("Authorization") String authorizationToken);

    @GetMapping("/songs/{id}")
    SongsResponse getSongDetails(@RequestParam("id") Long id, @RequestHeader("Authorization") String authorizationToken);
}
