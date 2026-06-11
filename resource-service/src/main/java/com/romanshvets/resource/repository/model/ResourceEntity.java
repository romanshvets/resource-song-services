package com.romanshvets.resource.repository.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "resource")
public class ResourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] content;
}