package vn.tuan.jobhunter.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.tuan.jobhunter.controller.errors.CustomException;
import vn.tuan.jobhunter.domain.Skill;
import vn.tuan.jobhunter.domain.User;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResultPaginationDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.userDTO.ResUserDTO;
import vn.tuan.jobhunter.repository.SkillRepository;
import vn.tuan.jobhunter.repository.SubscriberRepository;
import vn.tuan.jobhunter.service.SkillService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;

    private final SubscriberRepository subscriberRepository;
    public SkillServiceImpl(SkillRepository skillRepository, SubscriberRepository subscriberRepository) {
        this.skillRepository = skillRepository;
        this.subscriberRepository = subscriberRepository;
    }
    public Skill createSkill(Skill skill) {
        if (skillRepository.existsByName(skill.getName())) {
            throw new CustomException("Skill name already exists");
        }
        return skillRepository.save(skill);
    }
    public Skill updateSkill(Skill updatedSkill) {
        return skillRepository.findById(Long.valueOf(updatedSkill.getId()))
                .map(skill -> {
                    if (updatedSkill.getName() != null) {

                        if (!(updatedSkill.getName().equals(skill.getName())) &&(skillRepository.existsByName(updatedSkill.getName()))) {
                            throw new CustomException("Skill name already exists");
                        }
                        skill.setName(updatedSkill.getName());
                    }
                    return skillRepository.save(skill);
                }).orElseThrow(() -> new NoSuchElementException("Skill not found"));
    }
    public ResultPaginationDTO getAllSkills(Specification<Skill> spec, Pageable pageable) {
        Page<Skill> page=skillRepository.findAll(spec,pageable);

        List<Skill> users=page.getContent();


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
    public void deleteSkill(Long id) {
        Optional<Skill> skill=skillRepository.findById(id);
        if (!skill.isPresent()) {
            throw new NoSuchElementException("Skill not found");
        }
        Skill currentSkill=skill.get();
        currentSkill.getJobs().forEach(j->j.getSkills().remove(currentSkill));

        currentSkill.getSubscribers().forEach(j->j.getSkills().remove(currentSkill));


        skillRepository.deleteById(id);
    }

}
