package com.romanshvets.song.service;

import com.romanshvets.song.config.exception.SongSimpleException;
import com.romanshvets.song.config.exception.SongValidationException;
import com.romanshvets.song.repository.model.SongEntity;
import com.romanshvets.song.repository.SongRepository;
import com.romanshvets.song.service.model.SongDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.romanshvets.song.utils.SongValidationUtils.*;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository repository;
    private final ObjectMapper mapper;

    public Long createSong(Map<String, Object> params) {
        var validationErrors = validateParams(params);
        if (!validationErrors.isEmpty()) {
            throw new SongValidationException(400, "Validation error", validationErrors);
        }

        var song = mapper.convertValue(params, SongEntity.class);

        var persistedSong = repository.findById(song.getId());
        if (persistedSong.isPresent()) {
            throw new SongSimpleException(409, String.format("Metadata for resource ID=%s already exists", song.getId()));
        }

        repository.save(song);

        return song.getId();
    }

    public SongDto getSong(String idParam) {
        if (!validateIdParam(idParam)) {
            throw new SongSimpleException(400, String.format("Invalid value '%s' for ID. Must be a positive integer", idParam));
        }

        var song = repository.findById(Long.parseLong(idParam));
        if (song.isEmpty()) {
            throw new SongSimpleException(404, String.format("Song metadata for ID=%s not found", idParam));
        }

        return convertSongEntityToDto(song.get());
    }

    public Set<Long> deleteSongs(String ids) {
        var validationError = validateIdsParam(ids);
        if (validationError != null) {
            throw new SongSimpleException(400, validationError);
        }

        var idsToDelete = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        return repository.deleteByIdIn(idsToDelete)
                .stream()
                .map(SongEntity::getId).collect(Collectors.toSet());
    }

    private SongDto convertSongEntityToDto(SongEntity entity) {
        var dto = new SongDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setArtist(entity.getArtist());
        dto.setAlbum(entity.getAlbum());
        dto.setDuration(entity.getDuration());
        dto.setYear(entity.getYear());
        return dto;
    }
}
