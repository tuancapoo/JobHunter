package vn.tuan.jobhunter.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.tuan.jobhunter.domain.Company;
import vn.tuan.jobhunter.domain.dto.Meta;
import vn.tuan.jobhunter.domain.dto.ResultPaginationDTO;
import vn.tuan.jobhunter.repository.CompanyRepository;
import vn.tuan.jobhunter.service.CompanyService;

import java.util.List;
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
    public ResultPaginationDTO getAllCompanies(Pageable pageable) {
        Page<Company> pageCompanies = companyRepository.findAll(pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        Meta mt=new Meta();
        mt.setPage(pageCompanies.getNumber());
        mt.setPageSize(pageCompanies.getSize());

        mt.setPages(pageCompanies.getTotalPages());
        mt.setTotal(pageCompanies.getNumberOfElements());

        resultPaginationDTO.setMeta(mt);
        resultPaginationDTO.setObject(pageCompanies.getContent());
        return resultPaginationDTO;
    }

    @Override
    public Optional<Company> getCompanyById(Long id) {

        return companyRepository.findById(id);
    }

    @Override
    public Company updateCompany(Long id, Company updatedCompany) {


        return companyRepository.findById(id)
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
}
