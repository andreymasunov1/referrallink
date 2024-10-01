package com.pp.referrallink.repository;

import com.pp.referrallink.entity.Company;
import com.pp.referrallink.entity.Link;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

  @Query("SELECT l FROM Company l WHERE LOWER(l.category) LIKE LOWER(CONCAT('%', :search_term, '%')) " +
      "OR LOWER(l.name) LIKE LOWER(CONCAT('%', :search_term, '%'))")
  List<Company> findCompaniesBySearchTerm(@Param("search_term") String searchTerm);

  @Query("SELECT l FROM Company l WHERE l.name = :company_name")
  Company findByCompanyName(@Param("company_name") String companyName);

  @Query(value = "SELECT * FROM companies LIMIT 10", nativeQuery = true)
  List<Company> findTop10();
}
