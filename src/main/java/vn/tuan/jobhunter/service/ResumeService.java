package vn.tuan.jobhunter.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.tuan.jobhunter.domain.Resume;
import vn.tuan.jobhunter.domain.User;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResultPaginationDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResumeDTO.ResResumeCreateDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResumeDTO.ResResumeDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResumeDTO.ResUpdateResumeDTO;

import java.util.Optional;

@Service
public interface ResumeService {
    public ResResumeCreateDTO createResume(Resume resume);
    public ResUpdateResumeDTO updateResume(Resume resume);
    public void deleteResume(Long id);
    public Optional<Resume> getResumeById(Long id);
    public ResResumeDTO convertToResumeDTO(Resume resume);
    public ResultPaginationDTO getAllResumes(Specification<Resume> spec, Pageable pageable);

}
