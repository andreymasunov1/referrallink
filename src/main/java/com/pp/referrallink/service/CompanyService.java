package com.pp.referrallink.service;

import static com.pp.referrallink.enums.Categories.getIconByCategory;

import com.pp.referrallink.entity.Company;
import com.pp.referrallink.entity.Link;
import com.pp.referrallink.repository.CompanyRepository;
import com.pp.referrallink.repository.LinkRepository;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CompanyService {

  private final LinkRepository linkRepository;
  CompanyRepository companyRepository;
  public CompanyService(@Autowired CompanyRepository companyRepository,
      LinkRepository linkRepository) {
    this.companyRepository = companyRepository;
    this.linkRepository = linkRepository;
  }

  public List<Company> getCompanyBySearchTerm(String searchTerm) {
    return companyRepository.findCompaniesBySearchTerm(searchTerm);
  }

  public void deleteCompany(Company company) {
    companyRepository.delete(company);
  }

  public Company getCompanyByCompanyName(String companyName) {
    return companyRepository.findByCompanyName(companyName);
  }

  public List<Company> getInitialCompanies() {
    return companyRepository.findTop10();
  }

  public Company updateCompanyByLink(UUID id, Link updatedLink) {
    Link link = linkRepository.findById(id).orElse(null);
    assert link != null;
    Company oldCompany = link.getCompany();
    if (oldCompany != null) {
      oldCompany.setCategory(updatedLink.getCategory());
      oldCompany.setLogo(getCompanyIcon(updatedLink.getCompanyName(), updatedLink.getCategory()));
      oldCompany.setName(updatedLink.getCompanyName().trim().toLowerCase());
      return companyRepository.save(oldCompany);
    }
    return null;
  }

  public String getCompanyIcon(String companyName, String category) {
    String icon = getSpecificCompanyIcon(companyName);
    if (icon != null) {
      return STR."/logos/\{icon}";
    }
    return STR."/icons/\{getIconByCategory(category)}";
  }

  private String getSpecificCompanyIcon(String companyName) {

    String normalisedCompanyName = companyName.toLowerCase().trim().replace(" ", "_");

    ClassLoader classLoader = getClass().getClassLoader();
    URL resource = classLoader.getResource("static/logos");

    if (resource != null) {
      try {
        File directory = new File(resource.toURI());

        if (directory.isDirectory()) {
          File[] files = directory.listFiles();
          if (files != null) {
            for (File file : files) {
              if (file.isFile()) {
                if (file.getName().contains(normalisedCompanyName)) {
                  return file.getName();
                }
              }
            }
          }
        }
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Directory not found!");
    }
    return null;
  }
}
