package com.ahmedferjani.music_metadata.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class SearchTerm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String term;

    @OneToMany(mappedBy = "searchTerm")
    @ToString.Exclude
    private List<Song> songs;
}
