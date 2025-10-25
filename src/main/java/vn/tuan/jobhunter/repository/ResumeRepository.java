package vn.tuan.jobhunter.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.tuan.jobhunter.domain.Job;
import vn.tuan.jobhunter.domain.Resume;
import vn.tuan.jobhunter.domain.User;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long>, JpaSpecificationExecutor<Resume> {
    Boolean existsByUserAndJob(User user, Job job);
}
