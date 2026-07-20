package com.venue.app.util;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlUtil {

    /**
     * Valida se una stringa è un URL valido
     * @param imageUrl URL da validare
     * @return true se è un URL valido, false altrimenti
     */
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

    /**
     * Sanitizza un URL rimuovendo spazi extra
     * @param imageUrl URL da sanitizzare
     * @return URL sanitizzato
     */
    public static String sanitizeUrl(String imageUrl) {
        if (imageUrl == null) {
            return null;
        }
        return imageUrl.trim();
    }

    /**
     * Valida e sanitizza un URL
     * @param imageUrl URL da processare
     * @return URL valido o null
     */
    public static String validateAndSanitizeUrl(String imageUrl) {
        String sanitized = sanitizeUrl(imageUrl);
        if (isValidUrl(sanitized)) {
            return sanitized;
        }
        return null;
    }
}

