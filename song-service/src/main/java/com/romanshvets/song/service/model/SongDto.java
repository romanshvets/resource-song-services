package com.romanshvets.song.service.model;

import lombok.Data;

@Data
public class SongDto {
    private Long id;
    private String name;
    private String artist;
    private String album;
    private String duration;
    private String year;
}
