package com.pp.referrallink.security;

import com.pp.referrallink.entity.Customer;
import com.pp.referrallink.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

  private final CustomerRepository customerRepository;

  public MyUserDetailsService(@Autowired CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.printf("Loading user " + username);
    Customer user = customerRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not present with email: " + username));
    return (UserDetails) user;
  }
}
