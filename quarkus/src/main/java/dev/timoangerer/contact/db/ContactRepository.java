package dev.timoangerer.contact.db;

import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;

import dev.timoangerer.contact.model.Contact;
import dev.timoangerer.core.db.PersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
// @Singleton
public class ContactRepository {

  private static final String FIND_ALL = "SELECT * FROM contacts";
  private static final String FIND_ALL_BY_ID = "SELECT * FROM contacts WHERE id = ?";
  private static final String INSERT = "INSERT INTO contacts (name, email) VALUES (?, ?)";
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
        result.add(new Contact(UUID.fromString(resultSet.getString("id")), resultSet.getString("name"),
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
          return new Contact(UUID.fromString(resultSet.getString("id")), resultSet.getString("name"),
              resultSet.getString("email"));
        }
      }
    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
    return null;
  }

  public Contact insert(Contact contact) {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
      // statement.setObject(1, contact.getId());
      statement.setString(1, contact.getName());
      statement.setString(2, contact.getEmail());

      int affectedRows = statement.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Creating user failed, no rows affected.");
      }

      try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          String id = (generatedKeys.getString(1));
          contact.setId(UUID.fromString(id));
        } else {
          throw new SQLException("Creating user failed, no ID obtained.");
        }
      }

    } catch (SQLException e) {
      throw new PersistenceException(e);
    }

    return contact;
  }

  public Contact update(Contact contact) {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE)) {
      statement.setString(1, contact.getName());
      statement.setString(2, contact.getEmail());
      statement.setObject(3, contact.getId());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
    return contact;
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
