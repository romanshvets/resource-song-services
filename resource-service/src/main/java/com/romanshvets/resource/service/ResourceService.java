package com.romanshvets.resource.service;

import com.romanshvets.resource.config.exception.ResourceGenericException;
import com.romanshvets.resource.repository.model.ResourceEntity;
import com.romanshvets.resource.repository.ResourceRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static com.romanshvets.resource.utils.ResourceMetadataUtils.convertMetadataToParams;
import static com.romanshvets.resource.utils.ResourceValidationUtils.*;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository repository;

    @Value("${SONG_SERVICE_URL}")
    private String songServiceUrl;

    @Transactional
    public Long createResource(HttpServletRequest request) {
        var contentTypeError = validateContentType(request.getContentType());
        if (contentTypeError != null) {
            throw new ResourceGenericException(400, contentTypeError);
        }

        byte[] content;

        try {
            content = request.getInputStream().readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        var fileTypeError = validateStreamIsMP3(content);
        if (fileTypeError != null) {
            throw new ResourceGenericException(400, fileTypeError);
        }

        Metadata metadata = new Metadata();

        try (ByteArrayInputStream bis = new ByteArrayInputStream(content)) {
            new Mp3Parser().parse(bis, new DefaultHandler(), metadata, new ParseContext());
        } catch (TikaException | SAXException | IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return saveResourceAndMetadata(content, metadata);
    }

    public byte[] getResource(String idParam) {
        if (!validateIdParam(idParam)) {
            throw new ResourceGenericException(400, String.format("Invalid value '%s' for ID. Must be a positive integer", idParam));
        }

        var resource = repository.findById(Long.parseLong(idParam));
        if (resource.isEmpty()) {
            throw new ResourceGenericException(404, String.format("Resource with ID=%s not found", idParam));
        }

        return resource.get().getContent();
    }

    @Transactional
    public Set<Long> deleteResources(String ids) {
        var validationError = validateIdsParam(ids);
        if (validationError != null) {
            throw new ResourceGenericException(400, validationError);
        }

        var idsToDelete = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        return deleteResourceAndMetadata(idsToDelete);
    }

    @Transactional
    Long saveResourceAndMetadata(byte[] content, Metadata metadata) {
        var resource = new ResourceEntity();
        resource.setContent(content);

        repository.save(resource);

        var songMetadata = convertMetadataToParams(resource.getId(), metadata);

        try {
            RestClient.create().post()
                    .uri(songServiceUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(songMetadata)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
            throw new ResourceGenericException(400, String.format("Failed to save resource metadata for ID %s", resource.getId()));
        }

        return resource.getId();
    }

    @Transactional
    Set<Long> deleteResourceAndMetadata(Set<Long> ids) {
        var deletedResources = repository.deleteByIdIn(ids);

        var idsAsQueryParam = ids.stream().map(Object::toString).collect(Collectors.joining(","));

        try {
            RestClient.create().delete()
                    .uri(String.format("%s?id=%s", songServiceUrl, idsAsQueryParam))
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
            throw new ResourceGenericException(400, String.format("Failed to save resource metadata for IDs %s", idsAsQueryParam));
        }

        return deletedResources.stream()
                .map(ResourceEntity::getId)
                .collect(Collectors.toSet());
    }
}
