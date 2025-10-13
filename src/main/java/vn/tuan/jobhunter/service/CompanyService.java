package vn.tuan.jobhunter.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.tuan.jobhunter.domain.Company;
import vn.tuan.jobhunter.domain.Company;
import vn.tuan.jobhunter.domain.dto.ResultPaginationDTO;

import java.util.List;
import java.util.Optional;

@Service
public interface CompanyService {
    public Company createCompany(Company company);

    public ResultPaginationDTO getAllCompanies(Pageable pageable);

    public Optional<Company> getCompanyById(Long id);

    public Company updateCompany(Long id, Company updatedCompany);

    public void deleteCompany(Long id);

}
