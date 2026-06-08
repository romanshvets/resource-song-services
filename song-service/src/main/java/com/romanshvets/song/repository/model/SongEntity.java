package com.romanshvets.song.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "song")
public class SongEntity {
    @Id
    private Long id;

    private String name;
    private String artist;
    private String album;
    private String duration;
    private String year;
}