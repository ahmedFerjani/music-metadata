package com.ahmedferjani.music_metadata.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String platform;

    private String url;

    @ManyToOne
    @JoinColumn(name = "song_id", nullable = false)
    @ToString.Exclude
    private Song song;
}
