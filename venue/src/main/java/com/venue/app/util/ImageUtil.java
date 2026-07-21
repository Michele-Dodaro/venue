package com.venue.app.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Base64;

public class ImageUtil {

    public static String imageToBase64(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        byte[] imageBytes = file.getBytes();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    public static byte[] base64ToImageBytes(String base64String) {
        if (base64String == null || base64String.isEmpty()) {
            return null;
        }

        return Base64.getDecoder().decode(base64String);
    }

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
