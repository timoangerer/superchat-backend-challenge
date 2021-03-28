package dev.timoangerer.message.db;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import javax.sql.DataSource;

import dev.timoangerer.core.db.PersistenceException;
import dev.timoangerer.message.model.Message;

@ApplicationScoped
public class MessageRepository {

    private static final String INSERT = "insert into messages (contact_id, channel_id, sent_by_contact, sent_at, text) values (?, ?, ?, ?, ?)";

    private final DataSource dataSource;

    public MessageRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Message insert(Message message) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            statement.setObject(1, message.getContactId());
            statement.setObject(2, message.getChannelId());
            statement.setBoolean(3, message.getSentByContact());
            statement.setTimestamp(4, Timestamp.from(Instant.ofEpochSecond(Integer.parseInt(message.getSentAt()))));
            statement.setString(5, message.getText());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    String id = (generatedKeys.getString(1));
                    message.setId(UUID.fromString(id));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return message;
    }
}