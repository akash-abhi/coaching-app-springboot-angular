package com.coaching.util;

import org.springframework.stereotype.Component;

@Component
public class FileUtil {
    public static boolean isValidVideoFile(String fileName) {
        String[] validExtensions = {"mp4", "avi", "mov", "mkv", "flv", "wmv", "webm"};
        String fileExtension = getFileExtension(fileName).toLowerCase();
        
        for (String extension : validExtensions) {
            if (fileExtension.equals(extension)) {
                return true;
            }
        }
        return false;
    }

    public static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }
}
