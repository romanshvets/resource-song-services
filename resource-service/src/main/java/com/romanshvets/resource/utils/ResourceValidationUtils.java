package com.romanshvets.resource.utils;

import org.apache.tika.Tika;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

public class ResourceValidationUtils {

    public static String validateContentType(String contentType) {
        if (!"audio/mpeg".equalsIgnoreCase(contentType)) {
            return String.format("Invalid file format: %s. Only MP3 files are allowed", contentType);
        }

        return null;
    }

    public static String validateStreamIsMP3(byte[] content) {
        return validateContentType(new Tika().detect(content));
    }

    public static Map<String, String> validateParams(Map<String, Object> params) {
        if (params == null) {
            return Collections.singletonMap("metadata", "Song metadata is missing");
        }

        var result = new HashMap<String, String>();

        var idError = getIDError(params);
        if (idError != null) {
            result.put("id", idError);
        }

        var nameError = getNameError(params);
        if (nameError != null) {
            result.put("name", nameError);
        }

        var artistError = getArtistError(params);
        if (artistError != null) {
            result.put("artist", artistError);
        }

        var albumError = getAlbumError(params);
        if (albumError != null) {
            result.put("album", albumError);
        }

        var durationError = getDurationError(params);
        if (durationError != null) {
            result.put("duration", durationError);
        }

        var yearError = getYearError(params);
        if (yearError != null) {
            result.put("year", yearError);
        }

        return result;
    }

    public static boolean validateIdParam(String idParam) {
        return idStringValid(idParam);
    }

    public static String validateIdsParam(String ids) {
        if (ids == null || ids.isBlank()) {
            return "CSV string is required";
        }

        if (ids.length() > 200) {
            return "CSV string is too long: received 208 characters, maximum allowed is 200";
        }

        var invalidIds = new HashSet<String>();

        for (var id : ids.split(",")) {
            if (!idStringValid(id)) {
                invalidIds.add(id);
            }
        }

        if (!invalidIds.isEmpty()) {
            var invalidFieldsDescription = invalidIds.stream()
                    .map(v -> String.format("'%s'", v))
                    .collect(Collectors.joining(","));

            return String.format("Invalid ID format: %s. Only positive integers are allowed", invalidFieldsDescription);
        }

        return null;
    }

    private static String getIDError(Map<String, Object> params) {
        if (!params.containsKey("id")) {
            return "ID is missing";
        }

        if (!idObjectValid(params.get("id"))) {
            return "ID should be numeric integer";
        }

        return null;
    }

    private static boolean idStringValid(String idParam) {
        if (idParam == null || idParam.isBlank()) {
            return false;
        }

        try {
            return Long.parseLong(idParam) > 0L;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean idObjectValid(Object idParam) {
        if (!(idParam instanceof Number n)) {
            return false;
        }

        if (idParam instanceof Float || idParam instanceof Double) {
            return false;
        }

        return n.longValue() > 0;
    }

    private static String getNameError(Map<String, Object> params) {
        return validateStringParam(params, "name", "Song name", 1, 100);
    }

    private static String getArtistError(Map<String, Object> params) {
        return validateStringParam(params, "artist", "Artist name", 1, 100);
    }

    private static String getAlbumError(Map<String, Object> params) {
        return validateStringParam(params, "album", "Album name", 1, 100);
    }

    private static String getDurationError(Map<String, Object> params) {
        if (!params.containsKey("duration")) {
            return "Duration is required";
        }

        if (!(params.get("duration") instanceof String durationString) || durationString.length() != 5) {
            return "Duration must be in mm:ss format with leading zeros";
        }

        try {
            DateTimeFormatter.ofPattern("mm:ss").parse(durationString);
        } catch (DateTimeParseException e) {
            return "Duration must be in mm:ss format with leading zeros";
        }

        return null;
    }

    private static String getYearError(Map<String, Object> params) {
        if (!params.containsKey("year")) {
            return "Year is required";
        }

        if (!(params.get("year") instanceof String yearString) || yearString.length() != 4) {
            return "Year must be between 1900 and 2099";
        }

        try {
            var dateTime = DateTimeFormatter.ofPattern("yyyy").parse(yearString);
            var year = dateTime.get(ChronoField.YEAR);

            if (year < 1900 || year > 2099) {
                return "Year must be between 1900 and 2099";
            }
        } catch (DateTimeParseException e) {
            return "Year must be between 1900 and 2099";
        }

        return null;
    }

    private static String validateStringParam(Map<String, Object> params, String paramName, String paramPrettyName, int minLength, int maxLength) {
        if (!params.containsKey(paramName)) {
            return String.format("%s is required", paramPrettyName);
        }

        if (!(params.get(paramName) instanceof String name) || name.length() < minLength || name.length() > maxLength) {
            return String.format("%s must be between %s and %s characters", paramPrettyName, minLength, maxLength);
        }

        return null;
    }
}