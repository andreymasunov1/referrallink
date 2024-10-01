//package com.pp.referrallink.service;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import com.pp.referrallink.entity.Customer;
//import com.pp.referrallink.entity.Link;
//import com.pp.referrallink.repository.CustomerRepository;
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//class CustomerServiceImplTest {
//
//  @Mock
//  private CustomerRepository customerRepository;
//
//  @InjectMocks
//  private CustomerServiceImpl customerServiceImpl;
//
//  @BeforeEach
//  void setUp() {
//    MockitoAnnotations.openMocks(this);
//  }
//
//  @Test
//  void save_ShouldReturnSavedCustomer() {
//    //given
//    Customer customer = new Customer(1L, "test@test.com", "111111", "user", List.of(new Link()));
//    Mockito.when(customerRepository.save(customer)).thenReturn(customer);
//
//    //when
//    //String errorMessage = customerServiceImpl.save(customer, customer.getPassword());
//
//    //then
////    assertNotNull(savedCustomer);
////    assertEquals(customer.getId(), savedCustomer.getId());
////    assertEquals(customer.getEmail(), savedCustomer.getEmail());
////    assertEquals(customer.getPassword(), savedCustomer.getPassword());
////    assertEquals(customer, savedCustomer);
////    Mockito.verify(customerRepository, Mockito.times(1)).save(customer);
//  }
//
//  @Test
//  void findById() {
//  }
//
//  @Test
//  void findAll() {
//  }
//
//  @Test
//  void update() {
//  }
//
//  @Test
//  void delete() {
//  }
//
//  @Test
//  void findByEmail() {
//  }
//
//  @Test
//  void passwordConfirmationCheck() {
//  }
//}