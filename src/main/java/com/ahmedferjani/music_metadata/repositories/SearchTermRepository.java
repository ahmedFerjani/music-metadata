package com.ahmedferjani.music_metadata.repositories;

import com.ahmedferjani.music_metadata.domain.entities.SearchTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchTermRepository extends JpaRepository<SearchTerm, Long> {
}
