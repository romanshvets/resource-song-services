package com.romanshvets.resource.utils;

import org.apache.tika.metadata.Metadata;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResourceMetadataUtils {

    public static Map<String, Object> convertMetadataToParams(Long id, Metadata metadata) {
        var params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("name", metadata.get("dc:title"));
        params.put("artist", metadata.get("xmpDM:artist"));
        params.put("album", metadata.get("xmpDM:album"));
        params.put("duration", convertDuration(metadata.get("xmpDM:duration")));
        params.put("year", metadata.get("xmpDM:releaseDate"));

        return params;
    }

    private static String convertDuration(String durationInSeconds) {
        if (durationInSeconds == null || durationInSeconds.isBlank()) {
            return null;
        }

        var duration = Double.parseDouble(durationInSeconds);
        var minutes = (int) duration / 60;
        var seconds = (int) (duration - (minutes * 60));

        return String.format("%02d:%02d", minutes, seconds);
    }
}
