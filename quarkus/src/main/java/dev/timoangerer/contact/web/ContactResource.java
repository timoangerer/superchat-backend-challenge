package dev.timoangerer.contact.web;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import dev.timoangerer.contact.db.ContactRepository;
import dev.timoangerer.contact.model.Contact;

import java.util.List;
import java.util.UUID;

@Path("/contacts")
public class ContactResource {

  private final ContactRepository contactRepository;

  public ContactResource(ContactRepository contactRepository) {
    this.contactRepository = contactRepository;
  }

  // using `Response` requires extra code to enable runtime reflection
  // this is important if running against GraalVM
  // the serialization type can infer the serialization types at compile time
  // meaning that nothing needs to be done when the code below is used
  // maybe cover this in a separate post?
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Contact> all() {
    return contactRepository.findAll();
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Contact get(@PathParam("id") UUID id) {
    return contactRepository.findById(id);
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Contact post(Contact person) {
    return contactRepository.insert(
        new Contact(UUID.randomUUID(), person.getName(), person.getEmail()));
  }

  @PUT
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Contact put(@PathParam("id") UUID id, Contact person) {
    if (contactRepository.findById(id) == null) {
      throw new ContactNotFoundException(id);
    }
    return contactRepository.update(new Contact(id, person.getName(), person.getEmail()));
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public void delete(@PathParam("id") UUID id) {
    if (contactRepository.findById(id) == null) {
      throw new ContactNotFoundException(id);
    }
    contactRepository.deleteById(id);
  }
}
