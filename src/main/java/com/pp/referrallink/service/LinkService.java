package com.pp.referrallink.service;

import com.pp.referrallink.entity.Link;
import com.pp.referrallink.repository.LinkRepository;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LinkService {

  private LinkRepository linkRepository;

  public LinkService(@Autowired LinkRepository linkRepository) {
    this.linkRepository = linkRepository;
  }

  public Link createLink(Link link) {
    return linkRepository.save(link);
  }

  public Link updateLink(UUID id, Link link) {
    Link oldLink = linkRepository.findById(id).orElse(null);
    if (oldLink != null) {
      oldLink.setUrl(link.getUrl());
      oldLink.setCategory(link.getCategory());
      oldLink.setCompanyName(link.getCompanyName());
      return linkRepository.save(oldLink);
    }
    return null;
  }

  public void deleteLink(Link link) {
    linkRepository.delete(link);
  }

  public void deleteLinkById(UUID id) {
    linkRepository.deleteOneLinkById(id);
  }

  public Link getLink(UUID id) {
    return linkRepository.findById(id).orElse(null);
  }

  public Link getLinkById(UUID id) {
    return linkRepository.getLinkById(id);
  }

  public List<Link> getAllLinks() {
    return linkRepository.findAll();
  }

  public List<Link> getLinksByCustomerId(Long customerId) {
    return linkRepository.findLinksByCustomerId(customerId);
  }

  public List<Link> getLinksByCustomerEmail(String customerEmail) {
    return linkRepository.findLinksByCustomerEmail(customerEmail);
  }

  public List<Link> getLinksByCompanyName(String companyName) {
    return linkRepository.findLinksByCompanyName(companyName);
  }

  public String getRandomLinkByCompanyName(String companyName) {
    List<Link> links = linkRepository.findLinksByCompanyName(companyName);
    int randomIndex = new Random().nextInt(links.size());
    return links.get(randomIndex).getUrl();
  }

  public boolean checkIfLinkIsUnique(@NotBlank String url) {
    List<Link> links = linkRepository.findAll();
    for (Link link : links) {
      if (link.getUrl().equals(url)) {
        return false;
      }
    }
    return true;
  }
}
