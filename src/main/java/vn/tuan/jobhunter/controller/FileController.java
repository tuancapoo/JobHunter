package vn.tuan.jobhunter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.tuan.jobhunter.service.impl.FileService;

@RestController
@RequestMapping("/api/v1")

public class FileController {
    private final FileService fileService;
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
    @PostMapping("/files")
    public String uploadAFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("folder") String folder
    ) {
        //createFolderIfNotExist
        fileService.createDirectory(folder);
        //store file

        return file.getOriginalFilename()+ folder;

    }
}
