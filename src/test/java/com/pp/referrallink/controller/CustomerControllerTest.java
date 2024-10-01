package com.pp.referrallink.controller;

import com.pp.referrallink.entity.Customer;
import com.pp.referrallink.entity.Link;
import com.pp.referrallink.service.CustomerServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

//@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CustomerServiceImpl customerServiceImpl;

//  @InjectMocks
//  private CustomerController customerController;

  @Test
  void createCustomer() {
  }

  @Test
  void getCustomerById_ShouldReturnCustomer_WhenIdExists() throws Exception {
//    //need to mock authorisation
//
//    //given
//    Long customerId = 1L;
//    Customer customer = new Customer(1L, "test@test.com", "111111", "user", List.of(new Link()));
//    Mockito.when(customerServiceImpl.findById(customerId)).thenReturn(customer);
//
//    //when
//    mockMvc.perform(MockMvcRequestBuilders.get("/customers/1").contentType(MediaType.APPLICATION_JSON))
//        .andExpect(MockMvcResultMatchers.status().isOk())
//        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(customerId));
//    //then
//
//    Mockito.verify(customerServiceImpl, Mockito.times(1)).findById(customerId);

  }

//  @Test
//  void getAllCustomers() {
//  }
//
//  @Test
//  void updateCustomer() {
//  }
//
//  @Test
//  void deleteCustomer() {
//  }
}