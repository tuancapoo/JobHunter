package vn.tuan.jobhunter.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.tuan.jobhunter.domain.Company;
import vn.tuan.jobhunter.domain.dto.responseDTO.ResultPaginationDTO;
import vn.tuan.jobhunter.domain.dto.criterial.CompanyCriteriaDTO;

import java.util.Optional;

@Service
public interface CompanyService {
    public Company createCompany(Company company);

    public ResultPaginationDTO getAllCompanies(CompanyCriteriaDTO companyCriteriaDTO, Pageable pageable);

    public Optional<Company> getCompanyById(Long id);

    public Company updateCompany(Company updatedCompany);

    public void deleteCompany(Long id);

    public ResultPaginationDTO getAllCompanies(Specification<Company> spec, Pageable pageable);



}
