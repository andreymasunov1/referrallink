//package com.pp.referrallink.controller;
//
//import com.pp.referrallink.entity.Customer;
//import com.pp.referrallink.service.CustomerServiceImpl;
//import jakarta.validation.Valid;
//import java.util.List;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/customers")
//public class CustomerController {
//
//  private final CustomerServiceImpl customerServiceImpl;
//
//  public CustomerController(CustomerServiceImpl customerServiceImpl) {
//    this.customerServiceImpl = customerServiceImpl;
//  }
//
////  @GetMapping("/{id}")
////  public Customer getCustomer(@PathVariable Long id) {
////    return customerServiceImpl.findById(id);
////  }
//
////  @GetMapping
////  public List<Customer> getAllCustomers() {
////    return customerServiceImpl.findAll();
////  }
//
//  @PutMapping("/{id}")
//  public Customer updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer) {
//    return customerServiceImpl.update(customer);
//  }
//
//  @DeleteMapping("/{id}")
//  public void deleteCustomer(@PathVariable Long id) {
//    customerServiceImpl.delete(id);
//  }
//}
