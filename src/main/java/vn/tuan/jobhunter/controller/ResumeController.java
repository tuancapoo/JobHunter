package vn.tuan.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.tuan.jobhunter.domain.Resume;
import vn.tuan.jobhunter.domain.User;
import vn.tuan.jobhunter.domain.response.ApiResponse;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResultPaginationDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResumeDTO.ResResumeCreateDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResumeDTO.ResResumeDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResumeDTO.ResUpdateResumeDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.userDTO.ResUserDTO;
import vn.tuan.jobhunter.service.ResumeService;
import vn.tuan.jobhunter.util.annotation.ApiMessage;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ResumeController {
    private final ResumeService resumeService;
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }
    @PostMapping("/resumes")
    public ResponseEntity<ApiResponse<ResResumeCreateDTO>> createResume(@Valid @RequestBody Resume resume) {
        ResResumeCreateDTO resResumeCreateDTO = resumeService.createResume(resume);
        ApiResponse<ResResumeCreateDTO> response= new ApiResponse<>(HttpStatus.CREATED,"create successfully", resResumeCreateDTO,null);
        return ResponseEntity.ok().body(response);
    }
    @PutMapping("/resumes")
    public ResponseEntity<ApiResponse<ResUpdateResumeDTO>> updateResume(@RequestBody Resume resume) {
        ResUpdateResumeDTO resUpdateResumeDTO = resumeService.updateResume(resume);
        ApiResponse<ResUpdateResumeDTO> response= new ApiResponse<>(HttpStatus.OK,"update successfully", resUpdateResumeDTO,null);
        return ResponseEntity.ok().body(response);
    }
    @DeleteMapping("/resumes/id")
    public ResponseEntity<ApiResponse<Void>> deleteResume(@PathVariable("id") Long id) {
        resumeService.deleteResume(id);
        ApiResponse<Void> response=new ApiResponse<>(HttpStatus.OK,"delete successfully",null,null);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/resumes/{id}")
    public ResponseEntity<ApiResponse<ResResumeDTO>> getResume(@PathVariable Long id){
        return resumeService.getResumeById(id).map(resume -> {

            var response = new ApiResponse<>(HttpStatus.OK, "get Resume successfully", resumeService.convertToResumeDTO(resume)  , null);
            return ResponseEntity.ok(response);

        }).orElseGet(() -> {
            ApiResponse<ResResumeDTO> errorResponse = new ApiResponse<>(HttpStatus.NOT_FOUND,
                    "Không tìm thấy resume với ID: " + id, null, "RESUME_NOT_FOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        });
    }
    @GetMapping("/resumes")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getAllResumes(
            @Filter Specification<Resume> specification,
            Pageable pageable
            //@ModelAttribute UserCriteriaDTO userCriteriaDTO
    ){
        //ResultPaginationDTO result= userService.getAllUsers(userCriteriaDTO,pageable);
        ResultPaginationDTO result= resumeService.getAllResumes(specification,pageable);
        ApiResponse<ResultPaginationDTO> response=new ApiResponse<>(HttpStatus.OK,"get all users",result,null);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/resumes/by-user")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getResumesByUser(
            @Filter Specification<Resume> specification,
            Pageable pageable
            //@ModelAttribute UserCriteriaDTO userCriteriaDTO
    ){
        //ResultPaginationDTO result= userService.getAllUsers(userCriteriaDTO,pageable);
        ResultPaginationDTO result= resumeService.getAllResumes(specification,pageable);
        ApiResponse<ResultPaginationDTO> response=new ApiResponse<>(HttpStatus.OK,"get all users",result,null);
        return ResponseEntity.ok().body(response);
    }

}
