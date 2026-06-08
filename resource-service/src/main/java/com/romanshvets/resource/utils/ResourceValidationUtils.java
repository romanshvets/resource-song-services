package com.romanshvets.resource.utils;

import org.apache.tika.Tika;

import java.util.HashSet;
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
}