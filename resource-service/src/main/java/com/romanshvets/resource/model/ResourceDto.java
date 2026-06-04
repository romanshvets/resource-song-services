package com.romanshvets.resource.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "resource")
public class ResourceDto {
    @Id
    private Long id;

    private String name;
    private String artist;
    private String album;
    private String duration;
    private String year;
}