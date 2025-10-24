package vn.tuan.jobhunter.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.tuan.jobhunter.domain.Job;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.JobDTO.ResCreateJob;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.JobDTO.ResJobDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResultPaginationDTO;

@Service
public interface JobService {
    public ResCreateJob createJob(Job job);
    public ResultPaginationDTO getAllJobs(Specification<Job> spec, Pageable pageable);
    public void deleteJob(Long id);
    public ResJobDTO updateJob(Job job);
}
