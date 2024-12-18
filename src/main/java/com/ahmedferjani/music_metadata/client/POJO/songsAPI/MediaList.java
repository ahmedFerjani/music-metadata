package com.ahmedferjani.music_metadata.client.POJO.songsAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaList {

    private List<MediaResult> media;
}
