package com.romanshvets.resource.utils;

import org.apache.tika.metadata.Metadata;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResourceMetadataUtils {


//        var idError = getIDError(params);
//        if (idError != null) {
//            result.put("id", idError);
//        }
//
//        var nameError = getNameError(params);
//        if (nameError != null) {
//            result.put("name", nameError);
//        }
//
//        var artistError = getArtistError(params);
//        if (artistError != null) {
//            result.put("artist", artistError);
//        }
//
//        var albumError = getAlbumError(params);
//        if (albumError != null) {
//            result.put("album", albumError);
//        }
//
//        var durationError = getDurationError(params);
//        if (durationError != null) {
//            result.put("duration", durationError);
//        }
//
//        var yearError = getYearError(params);
//        if (yearError != null) {
//            result.put("year", yearError);
//        }
//
//        return result;

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

//    private String readFieldFromMetadata(Metadata metadata, String fieldName) {
//        if (metadata == null || !metadata.g)
//    }

//    if (params == null) {
//            return Collections.singletonMap("metadata", "Song metadata is missing");
//        }
//
//        var result = new HashMap<String, String>();
//
}

//"xmpDM:genre" -> {String[1]@11444} ["Test Genre"]
//"xmpDM:album" -> {String[1]@11446} ["Test Album"]
//"xmpDM:trackNumber" -> {String[1]@11448} ["1"]
//"xmpDM:releaseDate" -> {String[1]@11450} ["2025"]
//"xmpDM:artist" -> {String[1]@11452} ["Test Artist"]
//"dc:creator" -> {String[1]@11454} ["Test Artist"]
//"xmpDM:audioCompressor" -> {String[1]@11456} ["MP3"]
//"xmpDM:audioChannelType" -> {String[1]@11458} ["Stereo"]
//"version" -> {String[1]@11460} ["MPEG 3 Layer II..."]
//"xmpDM:logComment" -> {String[1]@11462} [""]
//"xmpDM:audioSampleRate" -> {String[1]@11464} ["44100"]
//"channels" -> {String[1]@11466} ["2"]
//"dc:title" -> {String[1]@11468} ["Test Title"]
//"xmpDM:duration" -> {String[1]@11470} ["7.8106350898742..."]
//"Content-Type" -> {String[1]@11472} ["audio/mpeg"]
//"samplerate" -> {String[1]@11474} ["44100"]