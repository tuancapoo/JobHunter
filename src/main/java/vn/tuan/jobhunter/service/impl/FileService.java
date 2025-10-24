package vn.tuan.jobhunter.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
    public void store(MultipartFile file, String folder) {
        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

        Path path=Paths.get(basePath, folder, fileName);
        try(InputStream inputStream=file.getInputStream()){
            Files.copy(inputStream,path, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            throw new RuntimeException("Cannot store file: " + path, e);
        }
    }

}
