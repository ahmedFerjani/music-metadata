package com.ahmedferjani.music_metadata.client;

import com.ahmedferjani.music_metadata.client.POJO.searchAPI.SearchResponse;
import com.ahmedferjani.music_metadata.client.POJO.songsAPI.SongsResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GeniusReactiveClient {
    private final WebClient webClient;

    public GeniusReactiveClient(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Based on a query, search for songs using an external API (Genius).
     *
     * @param query the search query, such as a song title or artist name
     * @return search results
     */
    public Mono<SearchResponse> searchSongs(String query) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search")
                        .queryParam("q", query)
                        .build())
                .retrieve()
                .bodyToMono(SearchResponse.class);
    }

    /**
     * Fetched song detailed information using an external API (Genius).
     *
     * @param songId song unique identifier.
     * @return song detailed information
     */
    public Mono<SongsResponse> getSongDetails(Long songId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/songs/{id}").build(songId))
                .retrieve()
                .bodyToMono(SongsResponse.class);
    }
}
