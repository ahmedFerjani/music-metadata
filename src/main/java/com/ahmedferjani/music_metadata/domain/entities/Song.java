package com.ahmedferjani.music_metadata.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Song {
    @Id
    private Long id;

    private String title;

    private String primaryArtistNames;

    private LocalDate releaseDate;

    private String coverPath;

    @ManyToOne
    @JoinColumn(name = "search_term_id")
    @ToString.Exclude
    private SearchTerm searchTerm;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Media> media;
}
