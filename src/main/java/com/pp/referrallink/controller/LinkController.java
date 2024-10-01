package com.pp.referrallink.controller;

import static com.pp.referrallink.enums.Categories.getIconByCategory;

import com.pp.referrallink.entity.Company;
import com.pp.referrallink.entity.Link;
import com.pp.referrallink.repository.CompanyRepository;
import com.pp.referrallink.service.CompanyService;
import com.pp.referrallink.service.CustomerServiceImpl;
import com.pp.referrallink.service.LinkService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LinkController {

  private final LinkService linkService;
  private final CustomerServiceImpl customerServiceImpl;
  private final CompanyRepository companyRepository;
  private final CompanyService companyService;


  public LinkController(@Autowired LinkService linkService, @Autowired CustomerServiceImpl customerServiceImpl,
      CompanyRepository companyRepository, @Autowired CompanyService companyService) {
    this.linkService = linkService;
    this.customerServiceImpl = customerServiceImpl;
    this.companyRepository = companyRepository;
    this.companyService = companyService;
  }

  //companyName industry linkValue   /addLink
  @PostMapping(path = "/addLink", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
  public String createLink(@Valid @RequestParam String companyName, @Valid @RequestParam String category, @Valid @RequestParam String url, HttpServletRequest request ) {

    Company company = companyRepository.findByCompanyName(companyName);

    if (linkService.checkIfLinkIsUnique(url)) {
      if (company == null) {
        company = new Company();
        company.setName(companyName.trim().toLowerCase());
        company.setCategory(category);
        company.setLogo(companyService.getCompanyIcon(companyName, category));
        companyRepository.save(company);
      }

      Link link = new Link();
      link.setCompanyName(companyName);
      link.setCategory(category);
      link.setUrl(url);
      link.setNumberOfClicks(0);
      link.setCustomer(customerServiceImpl.findByEmail(getCurrentUserName()).orElse(null));
      link.setCompany(company);
      linkService.createLink(link);

    } else {
      System.out.println("Link already exists");
    }
    return "redirect:/customer";
  }

//  @GetMapping
//  public List<Link> getAllLinks() {
//    return linkService.getAllLinks();
//  }

  @GetMapping("/{id}")
  public Link getLinkById(@PathVariable("id") UUID id) {
    return linkService.getLink(id);
  }

  @PutMapping("/{id}")
  public Link updateLink(@PathVariable UUID id, @Valid @RequestBody Link link) {
    return linkService.updateLink(id, link);
  }

//  @DeleteMapping("/{id}")
//  public void deleteLink(@PathVariable UUID id) {
//    linkService.deleteLink(id);
//  }

  public static String getCurrentUserName() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null) {
      Object principal = authentication.getPrincipal();

      if (principal instanceof UserDetails) {
        // Case for email/password login
        return ((UserDetails) principal).getUsername();
      } else if (principal instanceof OAuth2User) {
        // Case for OAuth2 login
        return ((OAuth2User) principal).getAttribute("email"); // or another attribute you prefer
      } else {
        // Default case if the principal is just a string (e.g., anonymous user)
        return principal.toString();
      }
    }
    return null; // No authentication found
  }
}
