package com.pp.referrallink.service;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {

  private String email;
  private String password;
  private String confirmPassword;
}
