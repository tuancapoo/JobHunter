package vn.tuan.jobhunter.controller;


import com.turkraft.springfilter.boot.Filter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import vn.tuan.jobhunter.domain.Skill;
import vn.tuan.jobhunter.domain.User;
import vn.tuan.jobhunter.domain.response.ApiResponse;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResultPaginationDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.userDTO.ResCreateUserDTO;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.userDTO.ResUserDTO;
import vn.tuan.jobhunter.repository.SkillRepository;
import vn.tuan.jobhunter.service.SkillService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SkillController {
    //Create
    private final SkillService skillService;
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }
    @PostMapping("/skills")
    public ResponseEntity<ApiResponse<Skill>> createSkill(@RequestBody Skill skill) {
        Skill newSkill = skillService.createSkill(skill);
        ApiResponse<Skill> response=new ApiResponse<>(HttpStatus.CREATED,"create successful",skill,null);
        return ResponseEntity.ok().body(response);
    }
    //Update
    @PutMapping("/skills")
    public ResponseEntity<ApiResponse<Skill>> updateSkill(@RequestBody Skill skill) {
        Skill updated = skillService.updateSkill(skill);
        ApiResponse<Skill> result = new ApiResponse<>(HttpStatus.OK, "updateUser", updated , null);
        return ResponseEntity.ok().body(result);
    }
    //GetAllSkill
    @GetMapping("/skills")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getAllSkills(
            @Filter Specification<Skill> specification,
            Pageable pageable
    ){
        ResultPaginationDTO result= skillService.getAllSkills(specification,pageable);
        ApiResponse<ResultPaginationDTO> response=new ApiResponse<>(HttpStatus.OK,"get all users",result,null);
        return ResponseEntity.ok().body(response);
    }
    //GetASkill
    @GetMapping("/skills/{id}")
    public ResponseEntity<ApiResponse<List<Skill>>> getASkill(@PathVariable("id") int id) {
        return null;


    }
    //DeleteSkill
    @DeleteMapping("/skills/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSkill(@PathVariable("id") Long id) {
        skillService.deleteSkill(id);
        ApiResponse<Void> result = new ApiResponse<>(HttpStatus.OK, "delete successful", null, null);
        return ResponseEntity.ok().body(result);
    }
}
