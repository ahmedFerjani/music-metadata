package com.ahmedferjani.music_metadata.client.POJO.songsAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SongsResponseData {

    private MediaList song;
}