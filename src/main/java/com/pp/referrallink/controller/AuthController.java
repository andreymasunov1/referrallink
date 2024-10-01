package com.pp.referrallink.controller;

import com.pp.referrallink.service.CustomerRequest;
import com.pp.referrallink.service.CustomerResponse;
import com.pp.referrallink.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

  final CustomerServiceImpl customerServiceImpl;

  public AuthController(@Autowired CustomerServiceImpl customerServiceImpl) {
    this.customerServiceImpl = customerServiceImpl;
  }

  @GetMapping("/login")
  public String loginForm() {
    return "login";
  }

  @GetMapping("/register")
  public String registerForm(Model model) {
    CustomerRequest customerRequest = new CustomerRequest();
    model.addAttribute("customerRequest", customerRequest);
    return "register";
  }

  @PostMapping("/register")
  public String registration(Model model, @ModelAttribute("customerRequest") CustomerRequest customerRequest) {

    CustomerResponse customerResponse = customerServiceImpl.save(customerRequest);

    if (customerResponse.getErrorMessage() != null) {
      model.addAttribute("notification",customerResponse.getErrorMessage());
      return "register";
    }

    model.addAttribute("notification", "Congratulation! Now you can login with new account!");
    return "login";
  }
}
