package vn.tuan.jobhunter.service.impl;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.tuan.jobhunter.controller.errors.CustomException;
import vn.tuan.jobhunter.domain.Resume;
import vn.tuan.jobhunter.domain.User;
import vn.tuan.jobhunter.domain.response.dto.criterial.UserCriteriaDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.JobDTO.ResJobDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResultPaginationDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResumeDTO.ResResumeCreateDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResumeDTO.ResResumeDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResumeDTO.ResUpdateResumeDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.userDTO.ResUserDTO;
import vn.tuan.jobhunter.repository.JobRepository;
import vn.tuan.jobhunter.repository.ResumeRepository;
import vn.tuan.jobhunter.repository.UserRepository;
import vn.tuan.jobhunter.service.ResumeService;
import vn.tuan.jobhunter.service.specification.UserSpecification;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    public ResumeServiceImpl(ResumeRepository resumeRepository, JobRepository jobRepository, UserRepository userRepository) {
        this.resumeRepository = resumeRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    public ResResumeCreateDTO createResume(Resume resume) {
        if (resume.getUser()==null || userRepository.existsById(Long.valueOf(resume.getUser().getId()))) {
            throw new CustomException("User not found");
        }
        if (resume.getJob()==null || jobRepository.existsById(Long.valueOf(resume.getJob().getId()))) {
            throw new CustomException("Job not found");
        }
        resume=resumeRepository.save(resume);
        return new ResResumeCreateDTO(resume.getId(),resume.getCreatedAt(), resume.getCreatedBy());

    }
    public ResUpdateResumeDTO updateResume(Resume resume) {
        if (!resumeRepository.findById(Long.valueOf(resume.getId())).isPresent()) {
            throw new CustomException("Resume not found");
        }
        Resume updated=resumeRepository.findById(Long.valueOf(resume.getId()))
                .map(job -> {
                    if (resume.getState()!=null) {
                        job.setState(resume.getState());
                    }
                    return resumeRepository.save(job);
                }).orElseThrow(() -> new NoSuchElementException("Skill not found"));
        return new ResUpdateResumeDTO(updated.getUpdatedBy(),updated.getUpdatedAt());
    }
    public void deleteResume(Long id) {
        Optional<Resume> resume=resumeRepository.findById(id);
        if (resume.isEmpty()){
            throw new CustomException("Resume not found");
        }
        this.resumeRepository.delete(resume.get());
    }

    public Optional<Resume> getResumeById(Long id){
        return resumeRepository.findById(Long.valueOf(id));
    }
    public ResResumeDTO convertToResumeDTO(Resume resume) {
        ResResumeDTO resResumeDTO=new ResResumeDTO();
        resResumeDTO.setId(resResumeDTO.getId());
        resResumeDTO.setEmail(resume.getEmail());
        resResumeDTO.setUrl(resume.getUrl());
        resResumeDTO.setCreatedAt(resume.getCreatedAt());
        resResumeDTO.setCreatedBy(resume.getCreatedBy());
        resResumeDTO.setUpdatedBy(resume.getUpdatedBy());
        resResumeDTO.setUpdatedAt(resume.getUpdatedAt());
        if (resume.getJob()!=null) {
            resResumeDTO.setCompanyName(resume.getJob().getCompany().getName());
        }
        resResumeDTO.setUser(new ResResumeDTO.UserName(resume.getUser().getId(),resume.getUser().getUsername()));
        resResumeDTO.setJob(new ResResumeDTO.JobResume(resume.getJob().getId(),resume.getJob().getName()));
        return resResumeDTO;
    }
    @Override
    public ResultPaginationDTO getAllResumes(Specification<Resume> spec, Pageable pageable) {
        Page<Resume> page=resumeRepository.findAll(spec,pageable);

        List<ResResumeDTO> users=page.getContent()
                .stream().map(item-> convertToResumeDTO(item))
                .collect(Collectors.toList());

        ResultPaginationDTO resultPaginationDTO=new ResultPaginationDTO();

        ResultPaginationDTO.Meta mt=new ResultPaginationDTO.Meta();
        mt.setPage(page.getNumber()+1);
        mt.setPageSize(page.getSize());

        mt.setPages(page.getTotalPages());
        mt.setTotal(page.getNumberOfElements());

        resultPaginationDTO.setMeta(mt);
        resultPaginationDTO.setResult(users);
        return resultPaginationDTO;

    }

}
