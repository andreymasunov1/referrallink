package com.pp.referrallink.controller;

import com.pp.referrallink.entity.ContactForm;
import com.pp.referrallink.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ContactController {

  @Autowired
  private EmailService emailService;

  @GetMapping("/contact")
  public String showContactForm(Model model) {
    model.addAttribute("contactForm", new ContactForm());
    return "contact";
  }

  @PostMapping("/contact/submit")
  public String submitContactForm(@ModelAttribute("contactForm") ContactForm contactForm, Model model) {
    try {
      emailService.sendContactEmail(contactForm);
      model.addAttribute("successMessage", "Your message has been sent successfully!");
    } catch (Exception e) {
      model.addAttribute("errorMessage", "An error occurred while sending your message. Please try again.");
    }
    return "contact";
  }
}
