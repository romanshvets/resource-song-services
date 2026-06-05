package com.romanshvets.resource.service;

import com.romanshvets.resource.exception.ResourceSimpleException;
import com.romanshvets.resource.model.ResourceDto;
import com.romanshvets.resource.repository.ResourceRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import static com.romanshvets.resource.utils.ResourceMetadataUtils.convertMetadataToParams;
import static com.romanshvets.resource.utils.ResourceValidationUtils.*;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository repository;

    @Value("${song.service.url}")
    private String songServiceUrl;

    @Transactional
    public Long createResource(HttpServletRequest request) {
        var contentTypeError = validateContentType(request.getContentType());
        if (contentTypeError != null) {
            throw new ResourceSimpleException(400, contentTypeError);
        }

        byte[] content;

        try {
            content = request.getInputStream().readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        var fileTypeError = validateStreamIsMP3(content);
        if (fileTypeError != null) {
            throw new ResourceSimpleException(400, fileTypeError);
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
            throw new ResourceSimpleException(400, String.format("Invalid value '%s' for ID. Must be a positive integer", idParam));
        }

        var resource = repository.findById(Long.parseLong(idParam));
        if (resource.isEmpty()) {
            throw new ResourceSimpleException(404, String.format("Resource with ID=%s not found", idParam));
        }

        return resource.get().getContent();
    }

    public Set<Long> deleteResources(String ids) {
//        var validationError = validateIdsParam(ids);
//        if (validationError != null) {
//            throw new SongSimpleException(400, validationError);
//        }
//
//        var idsToDelete = Arrays.stream(ids.split(","))
//                .map(Long::parseLong)
//                .collect(Collectors.toSet());
//
//        return repository.deleteByIdIn(idsToDelete)
//                .stream()
//                .map(SongDto::getId).collect(Collectors.toSet());
        return Collections.emptySet();
    }

    @Transactional
    Long saveResourceAndMetadata(byte[] content, Metadata metadata) {
        var resource = new ResourceDto();
        resource.setContent(content);

        repository.save(resource);

        var songMetadata = convertMetadataToParams(resource.getId(), metadata);

        var response = RestClient.create().post()
                .uri(songServiceUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(songMetadata)
                .retrieve()
                .toBodilessEntity();

        if (!HttpStatus.OK.equals(response.getStatusCode())) {
            throw new ResourceSimpleException(400, "Failed to save resource metadata");
        }

        return resource.getId();
    }
}
