package com.pp.referrallink.service;

import com.pp.referrallink.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

  private Customer customer;
  private String errorMessage;
}
