package com.pp.referrallink.controller;

import static com.pp.referrallink.controller.LinkController.getCurrentUserName;

import com.pp.referrallink.entity.Company;
import com.pp.referrallink.service.CompanyService;
import com.pp.referrallink.service.CustomerServiceImpl;
import com.pp.referrallink.service.LinkService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
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

  public WebController(@Autowired LinkService linkService, @Autowired CompanyService companyService,
      @Autowired CustomerServiceImpl customerServiceImpl) {
    this.linkService = linkService;
    this.companyService = companyService;
    this.customerServiceImpl = customerServiceImpl;
  }

  @GetMapping("/")
  public String home(Model model, HttpServletRequest request) {
    model.addAttribute("companies", companyService.getInitialCompanies());
    model.addAttribute("remoteUser", customerServiceImpl.findByEmail(getCurrentUserName()).orElse(null));
    return "index";
  }

  @GetMapping("/search")
  public String search(@RequestParam("query") String query, Model model, HttpServletRequest request) {
    if (!query.trim().isEmpty()) {
      List<Company> results = companyService.getCompanyBySearchTerm(query.toLowerCase().trim());
      model.addAttribute("companies", results);
      model.addAttribute("remoteUser", customerServiceImpl.findByEmail(getCurrentUserName()).orElse(null));
      return "/index";
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
