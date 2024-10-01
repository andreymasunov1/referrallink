package com.pp.referrallink.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"customer", "company"})
@Table(name = "links")
public class Link {

  @Id
  @GeneratedValue
  private UUID id;

  @NotBlank
  private String category;

  @NotBlank
  private String url;

  @NotBlank
  private String companyName;

  @Min(0)
  private int numberOfClicks;

  private double rating;

  private String country;

  private String description;

  @ManyToOne(fetch = FetchType.EAGER, targetEntity = Customer.class)
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @ManyToOne(fetch = FetchType.EAGER, targetEntity = Company.class)
  @JoinColumn(name = "company_id")
  private Company company;

  //  private Set<String> industrie = new HashSet<>();
//  @ElementCollection
//  @Column
//  @Id
//  @GeneratedValue(generator = "uuid-hibernate-generator")
//  @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
//  @Column(name="id")

//  private UUID id;


}
