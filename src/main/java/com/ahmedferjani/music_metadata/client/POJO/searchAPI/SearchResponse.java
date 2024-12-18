package com.ahmedferjani.music_metadata.client.POJO.searchAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse {

    private SearchResponseData response;
}
