package com.pp.referrallink.repository;

import com.pp.referrallink.entity.Link;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LinkRepository extends JpaRepository<Link, UUID> {

  @Query("SELECT l FROM Link l WHERE l.customer = :customer_id")
  List<Link> findLinksByCustomerId(@Param("customer_id") Long customerId);

  @Query("SELECT l FROM Link l JOIN l.customer c WHERE c.email = :email")
  List<Link> findLinksByCustomerEmail(@Param("email") String email);

  @Query("SELECT l FROM Link l WHERE l.companyName = :company_name")
  List<Link> findLinksByCompanyName(@Param("company_name") String companyName);

  @Query("SELECT l FROM Link l WHERE l.id = :id")
  Link getLinkById(@Param("id") UUID id);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM links WHERE id = :id", nativeQuery = true)
  void deleteOneLinkById(@Param("id") UUID id);
}
