package com.romanshvets.song.controller;

import com.romanshvets.song.model.SongDto;
import com.romanshvets.song.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> createSong(@RequestBody(required = false) Map<String, Object> params) {
        var songId = this.songService.createSong(params);

        return ResponseEntity.ok(Collections.singletonMap("id", songId));
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SongDto> getSong(@PathVariable("id") String id) {
        var song = this.songService.getSong(id);

        return ResponseEntity.ok(song);
    }

    @DeleteMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Set<Long>>> deleteSongs(@RequestParam("id") String ids) {
        var deletedSongIds = this.songService.deleteSongs(ids);

        return ResponseEntity.ok(Collections.singletonMap("ids", deletedSongIds));
    }
}
