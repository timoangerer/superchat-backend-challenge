package dev.timoangerer.contact.web;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ContactNotFoundExceptionHandler implements ExceptionMapper<ContactNotFoundException> {

  @Override
  public Response toResponse(ContactNotFoundException exception) {
    return Response.status(Response.Status.NOT_FOUND).build();
  }
}
