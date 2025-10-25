package vn.tuan.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.tuan.jobhunter.domain.response.ApiResponse;
import vn.tuan.jobhunter.domain.Company;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResultPaginationDTO;
import vn.tuan.jobhunter.service.CompanyService;

@RequestMapping("/api/v1")
@RestController
public class CompanyController {
    private final CompanyService companyService;
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
    @PostMapping("/companies")
    public ResponseEntity<ApiResponse<Company>> createCompany(@Valid @RequestBody Company company) {
        Company createdCompany = companyService.createCompany(company);
        ApiResponse<Company> response=new ApiResponse<>(HttpStatus.CREATED,"create successful",createdCompany,null);
        return ResponseEntity.ok().body(response);

    }

    @GetMapping("/companies")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getAllCompanies(
            @Filter Specification<Company> spec,
            Pageable pageable
            //@ModelAttribute CompanyCriteriaDTO companyCriteriaDTO
    ) {
        //ResultPaginationDTO companies = companyService.getAllCompanies(companyCriteriaDTO,pageable);
        ResultPaginationDTO companies = companyService.getAllCompanies(spec,pageable);
        System.out.println(companies);

        ApiResponse<ResultPaginationDTO> response=new ApiResponse<ResultPaginationDTO>(HttpStatus.OK,"get all companies",companies,null);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/companies/{id}")
    public ResponseEntity<ApiResponse<Company>> getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id).map(user -> {
            var response = new ApiResponse<>(HttpStatus.OK, "getUserById", user, null);
            return ResponseEntity.ok(response);

        }).orElseGet(() -> {
            ApiResponse<Company> errorResponse = new ApiResponse<>(HttpStatus.NOT_FOUND,
                    "Không tìm thấy user với ID: " + id, null, "USER_NOT_FOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        });
    }
    @PutMapping("/companies")
    public ResponseEntity<ApiResponse<Company>> updateCompany(@Valid @RequestBody Company company) {

        Company updated=companyService.updateCompany(company);
        ApiResponse<Company> response=new ApiResponse<>(HttpStatus.OK,"update successful",updated,null);
        return ResponseEntity.ok().body(response);
    }
    @DeleteMapping("companies/{id}")
    public ResponseEntity<ApiResponse<Company>> deleteCompany(@PathVariable Long id) {

        companyService.deleteCompany(id);
        ApiResponse<Company> response=new ApiResponse<>(HttpStatus.OK,"delete successful",null,null);
        return ResponseEntity.ok().body(response);
    }

}
