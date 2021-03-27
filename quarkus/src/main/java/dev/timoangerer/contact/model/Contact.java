package dev.timoangerer.contact.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.UUID;

// maybe write a super short post on how to serialize but not deserialize fields - https://stackoverflow.com/questions/16019834/ignoring-property-when-deserializing
// json ignore doesn't really cut it here
@JsonIgnoreProperties(value = { "id" }, allowGetters = true)
public class Contact {

  private UUID id;
  private String name;
  private String email;

  public Contact(UUID id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public Contact() {
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
