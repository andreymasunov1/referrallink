//package com.pp.referrallink.controller;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
//import com.pp.referrallink.service.CustomerServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.ui.Model;
//
//import com.pp.referrallink.service.CustomerRequest;
//import com.pp.referrallink.service.CustomerResponse;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//
//class AuthControllerTest {
//
//  @Mock
//  private CustomerServiceImpl customerServiceImpl;
//
//  @Mock
//  private Model model;
//
//  @InjectMocks
//  private AuthController authController;
//
//  private MockMvc mockMvc;
//
//  @BeforeEach
//  public void setup() {
//    MockitoAnnotations.initMocks(this);
//    mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
//  }
//
//  @Test
//  public void testLoginForm() throws Exception {
//    mockMvc.perform(get("/login"))
//        .andExpect(status().isOk())
//        .andExpect(view().name("login"));
//  }
//
//  @Test
//  public void testRegisterForm() throws Exception {
//    mockMvc.perform(get("/register"))
//        .andExpect(status().isOk())
//        .andExpect(view().name("register"))
//        .andExpect(model().attributeExists("customerRequest"));
//  }
//
//  @Test
//  public void testRegistrationSuccess() throws Exception {
//    // Prepare a successful customer response
//    CustomerResponse customerResponse = new CustomerResponse();
//    when(customerServiceImpl.save(any(CustomerRequest.class))).thenReturn(customerResponse);
//
//    mockMvc.perform(post("/register")
//            .param("name", "John Doe")
//            .param("email", "john.doe@example.com")
//            .param("password", "password123")
//            .param("confirmPassword", "password123"))
//        .andExpect(status().isOk())
//        .andExpect(view().name("login"))
//        .andExpect(model().attribute("notification", "Congratulation! Now you can login with new account!"));
//
//    verify(customerServiceImpl, times(1)).save(any(CustomerRequest.class));
//  }
//
//  @Test
//  public void testRegistrationFailure() throws Exception {
//    // Prepare a failed customer response with an error message
//    CustomerResponse customerResponse = new CustomerResponse();
//    customerResponse.setErrorMessage("Email already in use");
//    when(customerServiceImpl.save(any(CustomerRequest.class))).thenReturn(customerResponse);
//
//    mockMvc.perform(post("/register")
//            .param("name", "John Doe")
//            .param("email", "john.doe@example.com")
//            .param("password", "password123")
//            .param("confirmPassword", "password123"))
//        .andExpect(status().isOk())
//        .andExpect(view().name("register"))
//        .andExpect(model().attribute("notification", "Email already in use"));
//
//    verify(customerServiceImpl, times(1)).save(any(CustomerRequest.class));
//  }
//}