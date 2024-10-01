package com.pp.referrallink.controller;

import static com.pp.referrallink.controller.LinkController.getCurrentUserName;

import com.pp.referrallink.entity.Customer;
import com.pp.referrallink.entity.Link;
import com.pp.referrallink.enums.Categories;
import com.pp.referrallink.service.CompanyService;
import com.pp.referrallink.service.CustomerServiceImpl;
import com.pp.referrallink.service.LinkService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Transactional
public class CustomerMenuController {

  private final LinkService linkService;
  private final CustomerServiceImpl customerServiceImpl;
  private final CompanyService companyService;

  public CustomerMenuController(LinkService linkService, CustomerServiceImpl customerServiceImpl,
      CompanyService companyService) {
    this.linkService = linkService;
    this.customerServiceImpl = customerServiceImpl;
    this.companyService = companyService;
  }

  @GetMapping("/customer")
  public String loginForm(Model model, HttpServletRequest request) {
    model.addAttribute("links", linkService.getLinksByCustomerEmail(getCurrentUserName()));
    model.addAttribute("userDetails", customerServiceImpl.findByEmail(getCurrentUserName()).orElse(null));
    model.addAttribute("categories", Categories.values());
    model.addAttribute("newlink", new Link());
    model.addAttribute("remoteUser", getCurrentUserName());
    return "customer";
  }

  @PostMapping("customer/process/{id}")
  public String updateLink(@PathVariable("id") UUID id, @ModelAttribute Link link, @RequestParam String action) {

    if (linkService.checkIfLinkIsUnique(link.getUrl())) {
      if ("update".equals(action)) {
        Link updatedLink = new Link();
        updatedLink.setCompanyName(link.getCompanyName());
        updatedLink.setCategory(link.getCategory());
        updatedLink.setUrl(link.getUrl());
        linkService.updateLink(id, updatedLink);
        companyService.updateCompanyByLink(id, updatedLink);
        //need to update company?
      } else if ("delete".equals(action)) {
        String companyName = link.getCompanyName();
        linkService.deleteLinkById(link.getId());
        if (linkService.getLinksByCompanyName(companyName).isEmpty()) {
          companyService.deleteCompany(companyService.getCompanyByCompanyName(companyName));
        }
      }
    } else {
      System.out.println("Link already exists");
    }
    return "redirect:/customer";
  }

  @PostMapping("customer/updateCustomerInfo")
  public String updateCustomerInfo(@RequestParam String email, @RequestParam String password, HttpServletRequest request) {
    Customer customer = customerServiceImpl.findByEmail(getCurrentUserName()).orElse(null);
    if (customer == null) {
      System.out.println("Customer not found");
    } else {
      customer.setPassword(password);
      customer.setEmail(email);
      customerServiceImpl.update(customer);

      // Re-authenticate the user with the new email
      Authentication newAuth = new UsernamePasswordAuthenticationToken(
          customer.getEmail(),
          customer.getPassword(),
          customer.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(newAuth);

      // Optionally, invalidate the session and create a new one to avoid session fixation attacks
      //request.getSession().invalidate();
      //request.getSession(true);
    }
    return "redirect:/customer";
  }
}


//start page