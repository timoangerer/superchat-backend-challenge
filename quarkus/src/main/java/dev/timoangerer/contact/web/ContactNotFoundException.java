package dev.timoangerer.contact.web;

import java.util.UUID;

public class ContactNotFoundException extends RuntimeException {

  public ContactNotFoundException(UUID id) {
    super("Person with " + id.toString() + " does not exist!");
  }
}
