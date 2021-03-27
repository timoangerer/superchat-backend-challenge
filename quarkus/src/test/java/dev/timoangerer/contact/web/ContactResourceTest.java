package dev.timoangerer.contact.web;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import dev.timoangerer.contact.model.Contact;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@QuarkusTest
public class ContactResourceTest {

  @Test
  @Disabled
  public void testPostAndGet() {
    // test doesn't really work sine its connecting to the postgres container im running
    Contact person = new Contact(UUID.randomUUID(), "dan", "dan@foo.com");
    given()
        .when()
        .contentType(ContentType.JSON)
        .body(person)
        .post("/contacts")
        .then()
        .statusCode(200);
    Contact fromResponse = given().when().get("/contacts").then().statusCode(200).extract().as(Contact.class);
    assertNotEquals(person.getId(), fromResponse.getId());
    assertEquals(person.getName(), fromResponse.getName());
    assertEquals(person.getEmail(), fromResponse.getEmail());
  }
}
