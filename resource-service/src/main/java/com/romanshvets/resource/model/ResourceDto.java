package com.romanshvets.resource.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
@Entity(name = "resource")
public class ResourceDto {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private byte[] content;
}