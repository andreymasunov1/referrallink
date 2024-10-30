package com.pp.referrallink.controller;

import static com.pp.referrallink.controller.LinkController.getCurrentUserName;

import com.pp.referrallink.entity.Company;
import com.pp.referrallink.entity.Customer;
import com.pp.referrallink.service.CompanyService;
import com.pp.referrallink.service.CustomerServiceImpl;
import com.pp.referrallink.service.LinkService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {

  private final LinkService linkService;
  private final CompanyService companyService;
  private final CustomerServiceImpl customerServiceImpl;
  Logger logger = LoggerFactory.getLogger(this.getClass());

  public WebController(@Autowired LinkService linkService, @Autowired CompanyService companyService,
      @Autowired CustomerServiceImpl customerServiceImpl) {
    this.linkService = linkService;
    this.companyService = companyService;
    this.customerServiceImpl = customerServiceImpl;
  }

  @GetMapping("/")
  public String home(Model model, HttpServletRequest request) {
    logger.info("Loaded home page");
    model.addAttribute("companies", companyService.getInitialCompanies());
    model.addAttribute("remoteUser", customerServiceImpl.findByEmail(getCurrentUserName()).orElse(null));
    return "index";
  }

  @GetMapping("/search")
  public String search(@RequestParam("query") String query, Model model, HttpServletRequest request) {
    logger.info("Received data - query: {}", query);

    if (!query.trim().isEmpty()) {
      logger.info("Inside if");
      List<Company> results = companyService.getCompanyBySearchTerm(query.toLowerCase().trim());
      logger.info("results size: {}", results.size());
      logger.info("results list: {}", results);
      model.addAttribute("companies", results);
      Customer remoteUser = customerServiceImpl.findByEmail(
          getCurrentUserName()).orElse(null);
      model.addAttribute("remoteUser", remoteUser);
      logger.info("Added attribute remoteUser {}", remoteUser);
      return "index";
    } else {
      return "redirect:/";
    }
  }

//  @GetMapping("/load-more")
//  @ResponseBody
//  public List<Company> loadMore(@RequestParam("page") int page) {
//    return companyService.getCompaniesByPage(page);
//  }

  @GetMapping("/getRandomLink/{companyName}")
  @ResponseBody
  public String getRandomLink(@PathVariable String companyName) {
    String link = linkService.getRandomLinkByCompanyName(companyName);
    return link;
  }
}
