package com.ahmedferjani.music_metadata.mappers;

import com.ahmedferjani.music_metadata.domain.dto.MediaDTO;
import com.ahmedferjani.music_metadata.domain.entities.Media;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class MediaMapperTest {

    private final MediaMapper mediaMapper = Mappers.getMapper(MediaMapper.class);

    @Test
    void testToDTO() {
        // Prepare entity
        Media media = new Media();
        media.setId(1L);
        media.setUrl("https://example.com/video.mp4");
        media.setPlatform("youtube");

        // Map to DTO
        MediaDTO dto = mediaMapper.toDTO(media);

        // Verify mapping
        assertThat(dto).isNotNull();
        assertThat(dto.getUrl()).isEqualTo(media.getUrl());
        assertThat(dto.getPlatform()).isEqualTo(media.getPlatform());
    }
}
