package vn.tuan.jobhunter.controller;


import com.turkraft.springfilter.boot.Filter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.tuan.jobhunter.domain.Job;
import vn.tuan.jobhunter.domain.Skill;
import vn.tuan.jobhunter.domain.response.ApiResponse;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.JobDTO.ResCreateJob;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.JobDTO.ResJobDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResultPaginationDTO;
import vn.tuan.jobhunter.repository.JobRepository;
import vn.tuan.jobhunter.service.JobService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class JobController {
    private final JobService jobService;
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
    @PostMapping("/jobs")
    public ResponseEntity<ApiResponse<ResCreateJob>> createSkill(@RequestBody Job job) {
        ResCreateJob newjob = jobService.createJob(job);
        ApiResponse<ResCreateJob> response=new ApiResponse<>(HttpStatus.CREATED,"create successful",newjob,null);
        return ResponseEntity.ok().body(response);
    }
    @PutMapping("/jobs")
    public ResponseEntity<ApiResponse<ResJobDTO>> updateSkill(@RequestBody Job job) {
        ResJobDTO updated = jobService.updateJob(job);
        ApiResponse<ResJobDTO> result = new ApiResponse<>(HttpStatus.OK, "updateUser", updated , null);
        return ResponseEntity.ok().body(result);
    }
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSkill(@PathVariable("id") Long id) {
        jobService.deleteJob(id);
        ApiResponse<Void> result = new ApiResponse<>(HttpStatus.OK, "delete successful", null, null);
        return ResponseEntity.ok().body(result);
    }
    //GetAllJob
    @GetMapping("/jobs")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getAllSkills(
            @Filter Specification<Job> specification,
            Pageable pageable
    ){
        ResultPaginationDTO result= jobService.getAllJobs(specification,pageable);
        ApiResponse<ResultPaginationDTO> response=new ApiResponse<>(HttpStatus.OK,"get all users",result,null);
        return ResponseEntity.ok().body(response);
    }
}
