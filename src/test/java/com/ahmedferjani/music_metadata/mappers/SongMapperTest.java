package com.ahmedferjani.music_metadata.mappers;

import com.ahmedferjani.music_metadata.domain.dto.SongDTO;
import com.ahmedferjani.music_metadata.domain.entities.Song;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class SongMapperTest {

    private final SongMapper songMapper = Mappers.getMapper(SongMapper.class);

    @Test
    void testToDTO() {
        // Prepare entity
        Song song = new Song();
        song.setId(1L);
        song.setTitle("Imagine");
        song.setPrimaryArtistNames("John Lennon");
        song.setReleaseDate(LocalDate.of(1971, 10, 11));
        song.setCoverPath("imagine.jpg");

        // Map to DTO
        SongDTO dto = songMapper.toDTO(song);

        // Verify mapping
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(song.getId());
        assertThat(dto.getTitle()).isEqualTo(song.getTitle());
        assertThat(dto.getPrimaryArtistNames()).isEqualTo(song.getPrimaryArtistNames());
        assertThat(dto.getReleaseDate()).isEqualTo(song.getReleaseDate());
    }
}
