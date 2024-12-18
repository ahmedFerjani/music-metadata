package com.ahmedferjani.music_metadata.mappers;

import com.ahmedferjani.music_metadata.domain.dto.SearchTermDTO;
import com.ahmedferjani.music_metadata.domain.entities.SearchTerm;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SearchTermMapper {
    /**
     * Converts a {@link SearchTermDTO} object into a {@link SearchTerm} entity.
     *
     * @param searchTermDTO the {@link SearchTermDTO} to convert
     * @return the corresponding {@link SearchTerm} entity
     */
    SearchTerm toEntity(SearchTermDTO searchTermDTO);

    /**
     * Converts a {@link SearchTermDTO} object into a {@link SearchTerm} entity.
     *
     * @param searchTerm the {@link SearchTermDTO} to convert
     * @return the corresponding {@link SearchTerm} entity
     */
    SearchTermDTO toDTO(SearchTerm searchTerm);
}
