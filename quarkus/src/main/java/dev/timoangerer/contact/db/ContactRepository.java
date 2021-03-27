package dev.timoangerer.contact.db;

import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;

import dev.timoangerer.contact.model.Contact;
import dev.timoangerer.core.db.PersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
// @Singleton
public class ContactRepository {

  private static final String FIND_ALL = "SELECT * FROM contacts";
  private static final String FIND_ALL_BY_ID = "SELECT * FROM contacts WHERE id = ?";
  private static final String INSERT = "INSERT INTO contacts (id, name, email) VALUES (?, ?, ?)";
  private static final String UPDATE = "UPDATE contacts SET name = ?, email = ? WHERE id = ?";
  private static final String DELETE = "DELETE FROM contacts WHERE id = ?";

  private final DataSource dataSource;

  public ContactRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public List<Contact> findAll() {
    List<Contact> result = new ArrayList<>();
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_ALL);
        ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        result.add(
            new Contact(
                UUID.fromString(resultSet.getString("id")),
                resultSet.getString("name"),
                resultSet.getString("email")));
      }
    } catch (SQLException e) {
      throw new PersistenceException("boom", e.getCause());
    }
    return result;
  }

  public Contact findById(UUID id) {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_ID)) {
      statement.setObject(1, id);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return new Contact(
              UUID.fromString(resultSet.getString("id")),
              resultSet.getString("name"),
              resultSet.getString("email"));
        }
      }
    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
    return null;
  }

  public Contact insert(Contact person) {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(INSERT)) {
      statement.setObject(1, person.getId());
      statement.setString(2, person.getName());
      statement.setString(3, person.getEmail());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
    return person;
  }

  public Contact update(Contact person) {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE)) {
      statement.setString(1, person.getName());
      statement.setString(2, person.getEmail());
      statement.setObject(3, person.getId());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
    return person;
  }

  public boolean deleteById(UUID id) {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE)) {
      statement.setObject(1, id);
      return statement.executeUpdate() == 1;
    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
  }
}
