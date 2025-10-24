package vn.tuan.jobhunter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.tuan.jobhunter.controller.errors.CustomException;
import vn.tuan.jobhunter.domain.response.ApiResponse;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.FileDTO.ResUploadFileDTO;
import vn.tuan.jobhunter.service.impl.FileService;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1")

public class FileController {
    private final FileService fileService;
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
    @PostMapping("/files")
    public ResponseEntity<ApiResponse<ResUploadFileDTO>> uploadAFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("folder") String folder
    ) {
        //validateFile
        //File is empty
        if (file.isEmpty()) {
            throw new CustomException("File is empty");
        }
        //File extensions
        String name=file.getOriginalFilename();
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png","pdf","doc","webp");
        boolean isValid = allowedExtensions.stream().anyMatch(extension -> name.toLowerCase().endsWith(extension));
        if (!isValid) throw new CustomException("Invalid extension. on ly allows "+allowedExtensions.toString());
        //File size (max=50MB)

        //createFolderIfNotExist
        fileService.createDirectory(folder);
        //store file
        String fileName=fileService.store(file, folder);
        ResUploadFileDTO uploadFileDTO=new ResUploadFileDTO(fileName, Instant.now());
        ApiResponse<ResUploadFileDTO> apiResponse=new ApiResponse<>(HttpStatus.CREATED, "created successful", uploadFileDTO, null);;
        return ResponseEntity.ok().body(apiResponse);

    }
}
