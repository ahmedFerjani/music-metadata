package com.ahmedferjani.music_metadata.client.POJO.searchAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HitResult {

    private Long id;

    private String title;

    @JsonProperty("primary_artist_names")
    private String primaryArtistNames;

    @JsonProperty("release_date_components")
    private ReleaseDateComponents releaseDateComponents;

    @JsonProperty("header_image_thumbnail_url")
    private String headerImageThumbnailUrl;
}
