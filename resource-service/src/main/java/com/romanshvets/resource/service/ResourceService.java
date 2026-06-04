package com.romanshvets.resource.service;

import com.romanshvets.resource.model.ResourceDto;
import com.romanshvets.resource.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository repository;
    private final ObjectMapper mapper;

    public Long createResource(Map<String, Object> params) {
//        var validationErrors = validateParams(params);
//        if (!validationErrors.isEmpty()) {
//            throw new SongValidationException(400, "Validation error", validationErrors);
//        }
//
//        var song = mapper.convertValue(params, SongDto.class);
//
//        var persistedSong = repository.findById(song.getId());
//        if (persistedSong.isPresent()) {
//            throw new SongSimpleException(409, String.format("Metadata for resource ID=%s already exists", song.getId()));
//        }
//
//        repository.save(song);
//
//        return song.getId();
        return 5L;
    }

    public ResourceDto getResource(String idParam) {
//        if (!validateIdParam(idParam)) {
//            throw new SongSimpleException(400, String.format("Invalid value '%s' for ID. Must be a positive integer", idParam));
//        }
//
//        var song = repository.findById(Long.parseLong(idParam));
//        if (song.isEmpty()) {
//            throw new SongSimpleException(404, String.format("Song metadata for ID=%s not found", idParam));
//        }
//
//        return song.get();
        return null;
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
}
