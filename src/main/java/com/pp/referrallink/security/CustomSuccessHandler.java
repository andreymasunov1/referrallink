package com.pp.referrallink.security;

import com.pp.referrallink.entity.Customer;
import com.pp.referrallink.service.CustomerRequest;
import com.pp.referrallink.service.CustomerServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

  @Autowired
  CustomerServiceImpl customerServiceImpl;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    String redirectUrl = null;
    if(authentication.getPrincipal() instanceof DefaultOAuth2User) {
      DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();
      String email = userDetails.getAttribute("email") != null ? userDetails.getAttribute("email") : userDetails.getAttribute("login") + "gmail.com";

      CustomerRequest customerRequest = new CustomerRequest(email, null, null);
      customerServiceImpl.save(customerRequest);
    }
    redirectUrl = "/";
    new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
  }

}
