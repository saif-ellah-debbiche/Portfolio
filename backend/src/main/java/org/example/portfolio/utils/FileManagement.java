package org.example.portfolio.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileManagement {
    private static final String BLOG_UPLOAD_FOLDER="uploads/blogs";
    public static String uploadFile(MultipartFile file,String newContent) {
        try {
            // ✅ Create folder if not exists
            Path uploadDir = Paths.get(BLOG_UPLOAD_FOLDER);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // ✅ Sanitize file name to prevent path traversal
            String safeFileName = Paths.get(file.getOriginalFilename()).getFileName().toString();

            // ✅ Define path
            Path filePath = uploadDir.resolve(safeFileName);

            // ✅ Save file (streamed)
            Files.write(filePath, newContent.getBytes(StandardCharsets.UTF_8));

            return  filePath.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean deleteBlogByTitle(String title){
        String safeTitle = title.replaceAll("[^a-zA-Z0-9\\s-_]", "").trim();
        String filePath = BLOG_UPLOAD_FOLDER + safeTitle + ".md";

        File file = new File(filePath);

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("✅ File deleted successfully: " + filePath);
                return true;
            } else {
                System.out.println("⚠️ Failed to delete file: " + filePath);
                return false;
            }
        } else {
            System.out.println("❌ File not found: " + filePath);
            return false;
        }
    }
    public static String getBlogFileContent(String localPath) throws IOException {
        Path path = Path.of(localPath);
        return Files.readString(path, StandardCharsets.UTF_8);
    }
}
