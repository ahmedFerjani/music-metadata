package com.ahmedferjani.music_metadata.repositories;

import com.ahmedferjani.music_metadata.domain.entities.Song;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SongRepositoryTest {

    @Autowired
    private SongRepository songRepository;

    @Test
    void testSaveAndFindById() {
        Song song = new Song();
        song.setId(1L);
        song.setTitle("Imagine");
        song.setPrimaryArtistNames("John Lennon");
        song.setReleaseDate(LocalDate.of(1971, 10, 11));
        song.setCoverPath("imagine.jpg");

        Song savedSong = songRepository.save(song);

        // Check ID is generated
        assertThat(savedSong.getId()).isNotNull();

        // Retrieve by ID
        Optional<Song> foundSong = songRepository.findById(savedSong.getId());
        assertThat(foundSong).isPresent();
        assertThat(foundSong.get().getTitle()).isEqualTo("Imagine");
        assertThat(foundSong.get().getPrimaryArtistNames()).isEqualTo("John Lennon");
    }

    @Test
    void testFindById_nonExistent() {
        Optional<Song> song = songRepository.findById(999L);
        assertThat(song).isNotPresent();
    }

    @Test
    void testSaveMultipleSongs() {
        Song song1 = new Song();
        song1.setId(1L);
        song1.setTitle("Song One");

        Song song2 = new Song();
        song2.setId(2L);
        song2.setTitle("Song Two");

        songRepository.save(song1);
        songRepository.save(song2);

        assertThat(songRepository.findAll())
                .hasSize(2)
                .extracting(Song::getTitle)
                .containsExactlyInAnyOrder("Song One", "Song Two");
    }
}
