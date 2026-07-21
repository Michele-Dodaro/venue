package com.venue.app.util;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlUtil {

    public static boolean isValidUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return false;
        }

        try {
            new URI(imageUrl);
            return imageUrl.startsWith("http://") || imageUrl.startsWith("https://");
        } catch (URISyntaxException e) {
            return false;
        }
    }

    public static String sanitizeUrl(String imageUrl) {
        if (imageUrl == null) {
            return null;
        }
        return imageUrl.trim();
    }

    public static String validateAndSanitizeUrl(String imageUrl) {
        String sanitized = sanitizeUrl(imageUrl);
        if (isValidUrl(sanitized)) {
            return sanitized;
        }
        return null;
    }
}
