package com.ahmedferjani.music_metadata.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SongIdsDTO {
    private List<Long> songIds;
}
