package com.venue.app.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Base64;

public class ImageUtil {

    /**
     * Converte un'immagine (MultipartFile) in una stringa Base64
     * @param file File immagine
     * @return Stringa Base64 dell'immagine
     * @throws IOException Se c'è un errore durante la lettura del file
     */
    public static String imageToBase64(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        byte[] imageBytes = file.getBytes();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    /**
     * Converte una stringa Base64 in un array di byte
     * @param base64String Stringa Base64
     * @return Array di byte
     */
    public static byte[] base64ToImageBytes(String base64String) {
        if (base64String == null || base64String.isEmpty()) {
            return null;
        }

        return Base64.getDecoder().decode(base64String);
    }

    /**
     * Valida se il file è un'immagine valida
     * @param file File da validare
     * @return true se è un'immagine valida, false altrimenti
     */
    public static boolean isValidImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }

        String contentType = file.getContentType();
        return contentType != null && (
                contentType.equals("image/jpeg") ||
                contentType.equals("image/png") ||
                contentType.equals("image/gif") ||
                contentType.equals("image/webp")
        );
    }
}

