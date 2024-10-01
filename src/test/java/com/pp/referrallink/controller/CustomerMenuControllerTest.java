//package com.pp.referrallink.controller;
//
//import com.pp.referrallink.entity.Customer;
//import com.pp.referrallink.entity.Link;
//import com.pp.referrallink.enums.Categories;
//import com.pp.referrallink.service.CompanyService;
//import com.pp.referrallink.service.CustomerServiceImpl;
//import com.pp.referrallink.service.LinkService;
//import jakarta.servlet.http.HttpServletRequest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.ui.Model;
//
////import javax.servlet.http.HttpServletRequest;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//public class CustomerMenuControllerTest {
//
//  @Mock
//  private LinkService linkService;
//
//  @Mock
//  private CustomerServiceImpl customerServiceImpl;
//
//  @Mock
//  private CompanyService companyService;
//
//  @Mock
//  private Model model;
//
//  @Mock
//  private HttpServletRequest request;
//
//  @InjectMocks
//  private CustomerMenuController customerMenuController;
//
//  private MockMvc mockMvc;
//
//  @BeforeEach
//  public void setup() {
//    MockitoAnnotations.initMocks(this);
//    mockMvc = MockMvcBuilders.standaloneSetup(customerMenuController).build();
//  }
//
//  @Test
//  public void testLoginForm() throws Exception {
//    when(customerServiceImpl.findByEmail(anyString())).thenReturn(Optional.of(new Customer()));
//
//    mockMvc.perform(get("/customer"))
//        .andExpect(status().isOk())
//        .andExpect(view().name("customer"))
//        .andExpect(model().attributeExists("links", "userDetails", "categories", "newlink", "remoteUser"));
//  }
//
//  @Test
//  public void testUpdateLink_SuccessfulUpdate() throws Exception {
//    UUID linkId = UUID.randomUUID();
//    Link link = new Link();
//    link.setUrl("http://uniqueurl.com");
//
//    when(linkService.checkIfLinkIsUnique(anyString())).thenReturn(true);
//
//    mockMvc.perform(post("/customer/process/" + linkId)
//            .param("action", "update")
//            .with(csrf()))
//        .andExpect(status().is3xxRedirection())
//        .andExpect(redirectedUrl("/customer"));
//
//    verify(linkService, times(1)).updateLink(any(UUID.class), any(Link.class));
//    verify(companyService, times(1)).updateCompanyByLink(any(UUID.class), any(Link.class));
//  }
//
//  @Test
//  public void testUpdateLink_LinkAlreadyExists() throws Exception {
//    UUID linkId = UUID.randomUUID();
//    Link link = new Link();
//    link.setUrl("http://existingurl.com");
//
//    when(linkService.checkIfLinkIsUnique(anyString())).thenReturn(false);
//
//    mockMvc.perform(post("/customer/process/" + linkId)
//            .param("action", "update")
//            .with(csrf()))
//        .andExpect(status().is3xxRedirection())
//        .andExpect(redirectedUrl("/customer"));
//
//    verify(linkService, never()).updateLink(any(UUID.class), any(Link.class));
//    verify(companyService, never()).updateCompanyByLink(any(UUID.class), any(Link.class));
//  }
//
//  @Test
//  public void testDeleteLink() throws Exception {
//    UUID linkId = UUID.randomUUID();
//    Link link = new Link();
//    link.setUrl("http://existingurl.com");
//    link.setCompanyName("Test Company");
//
//    when(linkService.getLinksByCompanyName(anyString())).thenReturn(java.util.Collections.emptyList());
//
//    mockMvc.perform(post("/customer/process/" + linkId)
//            .param("action", "delete")
//            .with(csrf()))
//        .andExpect(status().is3xxRedirection())
//        .andExpect(redirectedUrl("/customer"));
//
//    verify(linkService, times(1)).deleteLinkById(any(UUID.class));
//    verify(companyService, times(1)).deleteCompany(any());
//  }
//
//  @Test
//  public void testUpdateCustomerInfo() throws Exception {
//    Customer customer = new Customer();
//    customer.setEmail("oldemail@example.com");
//    customer.setPassword("oldpassword");
//
//    when(customerServiceImpl.findByEmail(anyString())).thenReturn(Optional.of(customer));
//
//    mockMvc.perform(post("/customer/updateCustomerInfo")
//            .param("email", "newemail@example.com")
//            .param("password", "newpassword")
//            .with(csrf()))
//        .andExpect(status().is3xxRedirection())
//        .andExpect(redirectedUrl("/customer"));
//
//    verify(customerServiceImpl, times(1)).update(any(Customer.class));
//    verify(customerServiceImpl).findByEmail(anyString());
//  }
//}
