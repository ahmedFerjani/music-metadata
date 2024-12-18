package com.ahmedferjani.music_metadata.client.POJO.searchAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponseData {

    private List<HitList> hits;
}
