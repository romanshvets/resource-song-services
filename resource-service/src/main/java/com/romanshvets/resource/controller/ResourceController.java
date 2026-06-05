package com.romanshvets.resource.controller;

import com.romanshvets.resource.model.ResourceDto;
import com.romanshvets.resource.service.ResourceService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> createResource(HttpServletRequest request) {
        var resourceId = this.resourceService.createResource(request);

        return ResponseEntity.ok(Collections.singletonMap("id", resourceId));
    }

    @GetMapping(value = "/{id}", produces = "audio/mpeg")
    public ResponseEntity<byte[]> getResource(@PathVariable("id") String id) {
        var resourceContent = this.resourceService.getResource(id);

        return ResponseEntity.ok(resourceContent);
    }
//
//    @DeleteMapping(produces = APPLICATION_JSON_VALUE)
//    public ResponseEntity<Map<String, Set<Long>>> deleteSongs(@RequestParam("id") String ids) {
//        var deletedSongIds = this.songService.deleteSongs(ids);
//
//        return ResponseEntity.ok(Collections.singletonMap("ids", deletedSongIds));
//    }
}
