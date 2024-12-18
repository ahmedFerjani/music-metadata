package com.ahmedferjani.music_metadata.mappers;

import com.ahmedferjani.music_metadata.domain.dto.SongDTO;
import com.ahmedferjani.music_metadata.domain.entities.Song;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SongMapper {
    /**
     * Converts a {@link SongDTO} object into a {@link Song} entity.
     *
     * @param songDTO the {@link SongDTO} to convert
     * @return the corresponding {@link Song} entity
     */
    Song toEntity(SongDTO songDTO);

    /**
     * Converts a {@link SongDTO} object into a {@link Song} entity.
     *
     * @param song the {@link SongDTO} to convert
     * @return the corresponding {@link Song} entity
     */
    SongDTO toDTO(Song song);
}
