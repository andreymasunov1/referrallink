package com.pp.referrallink.controller;

import static com.pp.referrallink.controller.LinkController.getCurrentUserName;

import com.pp.referrallink.entity.Company;
import com.pp.referrallink.entity.Customer;
import com.pp.referrallink.entity.Link;
import com.pp.referrallink.enums.Categories;
import com.pp.referrallink.repository.CompanyRepository;
import com.pp.referrallink.service.CompanyService;
import com.pp.referrallink.service.CustomerServiceImpl;
import com.pp.referrallink.service.LinkService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
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
  private final CompanyRepository companyRepository;

  public CustomerMenuController(LinkService linkService, CustomerServiceImpl customerServiceImpl,
      CompanyService companyService, CompanyRepository companyRepository) {
    this.linkService = linkService;
    this.customerServiceImpl = customerServiceImpl;
    this.companyService = companyService;
    this.companyRepository = companyRepository;
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

  @PostMapping(path = "customer/addLink", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
  public String createLink(@Valid @RequestParam String companyName, @Valid @RequestParam String category, @Valid @RequestParam String url, HttpServletRequest request ) {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    try {
      Company company = companyRepository.findByCompanyName(companyName);

      logger.info("Received data - CompanyName: {}, Category: {}, URL: {}", companyName, category,
          url);

      if (linkService.checkIfLinkIsUnique(url)) {
        if (company == null) {
          company = new Company();
          company.setName(companyName.trim().toLowerCase());
          company.setCategory(category);
          company.setLogo(companyService.getCompanyIcon(companyName, category));
          companyRepository.save(company);
          logger.info("Company saved");
        }

        Link link = new Link();
        link.setCompanyName(companyName.trim().toLowerCase());
        link.setCategory(category);
        link.setUrl(url);
        link.setNumberOfClicks(0);
        link.setCustomer(customerServiceImpl.findByEmail(getCurrentUserName()).orElse(null));
        link.setCompany(company);

        logger.info("Creating link for company: {}, URL: {}", companyName, url);

        linkService.createLink(link);

      } else {
        logger.warn("Link already exists for URL: {}", url);
      }
      if (logger.isInfoEnabled()) {
        logger.info("AddLink request completed");
      }
    }
    catch (Exception e) {
      logger.error("Error occurred while creating link: {}", e.getMessage(), e);
      throw e;
    }

    return "redirect:/customer";
  }

  @PostMapping("customer/process/{id}")
  public String updateLink(@PathVariable("id") UUID id, @ModelAttribute Link link, @RequestParam String action) {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    //need to cover case when we are trying to change link to the value which is already presented but not the same which we are editing
      if ("update".equals(action)) {
        logger.info("Update action is started");
          Link updatedLink = new Link();
          updatedLink.setCompanyName(link.getCompanyName());
          updatedLink.setCategory(link.getCategory());
          updatedLink.setUrl(link.getUrl());
          linkService.updateLink(id, updatedLink);
        logger.info("Link is updated ");
          companyService.updateCompanyByLink(id, updatedLink);
        logger.info("Company is updated");
        } else if ("delete".equals(action)) {
        logger.info("Delete action is started");
        String companyName = link.getCompanyName();
        linkService.deleteLinkById(link.getId());
        logger.info("Link is deleted");
        if (linkService.getLinksByCompanyName(companyName).isEmpty()) {
          logger.info("There is no more links for this company. Company have to be deleted");
          companyService.deleteCompany(companyService.getCompanyByCompanyName(companyName));
          logger.info("Company is deleted");
        }
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