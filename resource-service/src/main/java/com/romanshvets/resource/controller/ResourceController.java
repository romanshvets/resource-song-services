package com.romanshvets.resource.controller;

import com.romanshvets.resource.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping(consumes = "audio/mpeg", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> createResource(InputStream inputStream) {
//        var songId = this.resourceService.createResource(params);
//
//        return ResponseEntity.ok(Collections.singletonMap("id", songId));
        return ResponseEntity.ok(Collections.emptyMap());
    }
//
//    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
//    public ResponseEntity<SongDto> getSong(@PathVariable("id") String id) {
//        var song = this.songService.getSong(id);
//
//        return ResponseEntity.ok(song);
//    }
//
//    @DeleteMapping(produces = APPLICATION_JSON_VALUE)
//    public ResponseEntity<Map<String, Set<Long>>> deleteSongs(@RequestParam("id") String ids) {
//        var deletedSongIds = this.songService.deleteSongs(ids);
//
//        return ResponseEntity.ok(Collections.singletonMap("ids", deletedSongIds));
//    }
}
