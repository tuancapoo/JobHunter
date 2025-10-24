package vn.tuan.jobhunter.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    @Value("${tuan.upload-file.basepath}")
    private String basePath;
    public void createDirectory(String folder) {
        Path uploadDir = Paths.get(basePath, folder);
        try {
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
                System.out.println(" Created  ");
            }
            else {
                System.out.println(" Already Exist ");
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot create directory: " + uploadDir, e);
        }

    }
}
