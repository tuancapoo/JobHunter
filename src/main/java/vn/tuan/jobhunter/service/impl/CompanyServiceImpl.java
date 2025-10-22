package vn.tuan.jobhunter.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.tuan.jobhunter.domain.Company;
import vn.tuan.jobhunter.domain.response.dto.responseDTO.ResultPaginationDTO;
import vn.tuan.jobhunter.domain.response.dto.criterial.CompanyCriteriaDTO;
import vn.tuan.jobhunter.repository.CompanyRepository;
import vn.tuan.jobhunter.service.CompanyService;
import vn.tuan.jobhunter.service.specification.CompanySpecification;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }
    @Override
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public ResultPaginationDTO getAllCompanies(CompanyCriteriaDTO companyCriteriaDTO, Pageable pageable) {
        Specification<Company> spec=CompanySpecification.companySpecification(companyCriteriaDTO);
        Page<Company> pageCompanies = companyRepository.findAll(spec,pageable);


        ResultPaginationDTO.Meta mt=new ResultPaginationDTO.Meta();
        mt.setPage(pageCompanies.getNumber()+1);
        mt.setPageSize(pageCompanies.getSize());
        mt.setPages(pageCompanies.getTotalPages());
        mt.setTotal(pageCompanies.getNumberOfElements());

        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        resultPaginationDTO.setMeta(mt);
        resultPaginationDTO.setResult(pageCompanies.getContent());
        return resultPaginationDTO;
    }

    @Override
    public Optional<Company> getCompanyById(Long id) {

        return companyRepository.findById(id);
    }

    @Override
    public Company updateCompany(Company updatedCompany) {

        return companyRepository.findById(Long.valueOf(updatedCompany.getId()))
                .map(company -> {
                    if (updatedCompany.getAddress() != null)
                        company.setAddress(updatedCompany.getAddress());
                    if (updatedCompany.getName() != null)
                        company.setName(updatedCompany.getName());
                    if (updatedCompany.getDescription()!= null)
                        company.setDescription(updatedCompany.getDescription());
                    if (updatedCompany.getLogo() != null)
                        company.setLogo(updatedCompany.getLogo());
                    return companyRepository.save(company);
                }).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Override
    public void deleteCompany(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new NoSuchElementException("User not found");
        }
        companyRepository.deleteById(id);
    }

    @Override
    public ResultPaginationDTO getAllCompanies(Specification<Company> spec, Pageable pageable) {
        Page<Company> pageCompanies = companyRepository.findAll(spec,pageable);

        ResultPaginationDTO.Meta mt=new ResultPaginationDTO.Meta();
        mt.setPage(pageCompanies.getNumber()+1);
        mt.setPageSize(pageCompanies.getSize());
        mt.setPages(pageCompanies.getTotalPages());
        mt.setTotal(pageCompanies.getNumberOfElements());

        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        resultPaginationDTO.setMeta(mt);
        resultPaginationDTO.setResult(pageCompanies.getContent());
        return resultPaginationDTO;    }
}
