package vn.tuan.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.tuan.jobhunter.domain.Skill;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long>, JpaSpecificationExecutor<Skill> {
    Optional<Skill> findById(Long Id);
    Optional<Skill> findByName(String name);
    Boolean existsByName(String name);
    List<Skill> findByIdIn(Collection<Long> ids);



}
