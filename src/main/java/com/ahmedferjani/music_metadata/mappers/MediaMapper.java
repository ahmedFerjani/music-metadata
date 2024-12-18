package com.ahmedferjani.music_metadata.mappers;

import com.ahmedferjani.music_metadata.domain.dto.MediaDTO;
import com.ahmedferjani.music_metadata.domain.entities.Media;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MediaMapper {

    /**
     * Converts a {@link MediaDTO} object into a {@link Media} entity.
     *
     * @param mediaDTO the {@link MediaDTO} to convert
     * @return the corresponding {@link Media} entity
     */
    Media toEntity(MediaDTO mediaDTO);

    /**
     * Converts a {@link Media} object into a {@link Media} entity.
     *
     * @param media the {@link Media} to convert
     * @return the corresponding {@link MediaDTO} entity
     */
    MediaDTO toDTO(Media media);
}
