package com.ahmedferjani.music_metadata.repositories;

import com.ahmedferjani.music_metadata.domain.entities.SearchTerm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SearchTermRepositoryTest {

    @Autowired
    private SearchTermRepository searchTermRepository;

    @Test
    void testSaveAndFindById() {
        // Create and save a search term
        SearchTerm term = new SearchTerm();
        term.setTerm("rock music");
        SearchTerm savedTerm = searchTermRepository.save(term);

        // Verify ID was generated
        assertThat(savedTerm.getId()).isNotNull();

        // Find by ID
        Optional<SearchTerm> foundTerm = searchTermRepository.findById(savedTerm.getId());
        assertThat(foundTerm).isPresent();
        assertThat(foundTerm.get().getTerm()).isEqualTo("rock music");
    }

    @Test
    void testFindById_nonExistent() {
        // Search for an ID that doesn't exist
        Optional<SearchTerm> foundTerm = searchTermRepository.findById(999L);
        assertThat(foundTerm).isNotPresent();
    }

    @Test
    void testSaveMultipleTerms() {
        SearchTerm term1 = new SearchTerm();
        term1.setTerm("pop");

        SearchTerm term2 = new SearchTerm();
        term2.setTerm("jazz");

        searchTermRepository.save(term1);
        searchTermRepository.save(term2);

        assertThat(searchTermRepository.findAll()).hasSize(2)
                .extracting(SearchTerm::getTerm)
                .containsExactlyInAnyOrder("pop", "jazz");
    }
}
