package com.pp.referrallink.service;

import com.pp.referrallink.entity.Customer;
import com.pp.referrallink.enums.Roles;
import com.pp.referrallink.repository.CustomerRepository;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
      + "abcdefghijklmnopqrstuvwxyz"
      + "0123456789"
      + "!@#$%^&*()_+";

  private CustomerRepository customerRepository;

  public CustomerServiceImpl(@Autowired CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public CustomerResponse save(CustomerRequest customerRequest) {

    CustomerServiceImpl customerServiceImpl = this;
    String normalisedEmail = customerRequest.getEmail().toLowerCase();

    if (!customerServiceImpl.findByEmail(normalisedEmail).isEmpty()) {
      return new CustomerResponse(null, "Email already in use");
    }

    if (customerRequest.getPassword() == null && customerRequest.getConfirmPassword() == null) {
      String generatedPassword = generateRandomString(10);
      customerRequest.setPassword(generatedPassword);
      customerRequest.setConfirmPassword(generatedPassword);
    }

    if (!customerRequest.getPassword().equals(customerRequest.getConfirmPassword())) {
      return new CustomerResponse(null, "Passwords do not match");
    }

    Customer customer = new Customer();
    customer.setEmail(normalisedEmail);
    customer.setPassword(new BCryptPasswordEncoder().encode(customerRequest.getPassword()));
    customer.setRole(Roles.USER.role);
    Customer savedCustomer = customerRepository.save(customer);

    return new CustomerResponse(savedCustomer, null);
  }

  public Customer findById(Long id) {
    return customerRepository.findById(id).orElse(null);
  }

  public List<Customer> findAll() {
    return (List<Customer>) customerRepository.findAll();
  }

  public Customer update(Customer customer) {
    Customer oldCustomer = customerRepository.findById(customer.getId()).orElse(null);
    if (oldCustomer != null) {
      oldCustomer.setEmail(customer.getEmail());
      oldCustomer.setRole(customer.getRole());
      return customerRepository.save(oldCustomer);
    }
    return null;
  }

  public void delete(Long id){
    customerRepository.deleteById(id);
  }

  public Optional<Customer> findByEmail(String email) {
    return customerRepository.findByEmail(email);
  }

  public static @NotBlank String passwordConfirmationCheck(String password, String confirmPassword) {
    return password.equals(confirmPassword) ? password : null;
  }

  public static String generateRandomString(int length) {
    Random random = new Random();
    StringBuilder sb = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      int index = random.nextInt(CHARACTERS.length());
      sb.append(CHARACTERS.charAt(index));
    }
    return sb.toString();
  }
}
