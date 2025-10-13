package vn.tuan.jobhunter.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.tuan.jobhunter.domain.ApiResponse;
import vn.tuan.jobhunter.domain.Company;
import vn.tuan.jobhunter.domain.User;
import vn.tuan.jobhunter.domain.dto.ResultPaginationDTO;
import vn.tuan.jobhunter.repository.CompanyRepository;
import vn.tuan.jobhunter.service.CompanyService;

import java.util.List;
import java.util.Optional;

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
            @RequestParam("current") Optional<String> currentOptional,
            @RequestParam("pageSize") Optional<String> pageSizeOptional) {
        String scurrent=currentOptional.orElse("");
        String spageSize=pageSizeOptional.orElse("");

        int pageNumber=Integer.parseInt(scurrent)-1;
        int pageSize=Integer.parseInt(spageSize);

        Pageable pageable=PageRequest.of(pageNumber,pageSize);

        ResultPaginationDTO companies = companyService.getAllCompanies(pageable);




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
    @PutMapping("/companies/{id}")
    public ResponseEntity<ApiResponse<Company>> updateCompany(@PathVariable Long id, @Valid @RequestBody Company company) {
        Company updated=companyService.updateCompany(id,company);
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
