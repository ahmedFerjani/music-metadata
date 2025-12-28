package com.ahmedferjani.music_metadata.repositories;

import com.ahmedferjani.music_metadata.domain.entities.Media;
import com.ahmedferjani.music_metadata.domain.entities.Song;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MediaRepositoryTest {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private SongRepository songRepository;

    @Test
    void testFindBySongId() {
        Song song = new Song();
        song.setId(1L);
        song.setTitle("Test Song");
        songRepository.save(song);

        Media media = new Media();
        media.setPlatform("youtube");
        media.setUrl("http://youtube.com/video1");
        media.setSong(song);
        mediaRepository.save(media);

        List<Media> mediaList = mediaRepository.findBySongId(song.getId());
        assertThat(mediaList).hasSize(1)
                .extracting(Media::getUrl)
                .containsExactly("http://youtube.com/video1");
    }
}
