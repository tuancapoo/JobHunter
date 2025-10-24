package vn.tuan.jobhunter.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.tuan.jobhunter.domain.Skill;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResultPaginationDTO;

@Service
public interface SkillService {
    public Skill createSkill(Skill skill);
    public Skill updateSkill(Skill skill);
    public ResultPaginationDTO getAllSkills(Specification<Skill> spec, Pageable pageable);
    public void deleteSkill(Long id);
}
