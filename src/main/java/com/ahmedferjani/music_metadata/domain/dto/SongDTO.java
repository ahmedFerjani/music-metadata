package com.ahmedferjani.music_metadata.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class SongDTO {
    private Long id;
    private String title;
    private String primaryArtistNames;
    private LocalDate releaseDate;
}
