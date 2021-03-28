package dev.timoangerer.channel.whatsapp.db;

import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;

import dev.timoangerer.channel.ChannelType;
import dev.timoangerer.contact.model.Contact;
import dev.timoangerer.core.db.PersistenceException;
import dev.timoangerer.webhooks.whatsapp.model.WhatsappMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.sql.Statement;

@ApplicationScoped
public class WhatsappChannelRepository {

    private static final String FIND_CONTACT_BY_ID = "select contacts.* from whatsapp_channels inner join channels on channels.id = whatsapp_channels.channel_id inner join contacts on contacts.id = channels.contact_id where wa_id = ?";

    private static final String FIND_CHANNEL_BY_ID = "select channels.id from whatsapp_channels inner join channels on channels.id = whatsapp_channels.channel_id where wa_id = ?";

    private static final String INSERT = "with first_insert as ( insert into channels (contact_id, channel_type_id) values (?, ?) returning id as channel_id ) insert into whatsapp_channels (channel_id, wa_id) values ( (select channel_id from first_insert), ?)";

    private final DataSource dataSource;

    public WhatsappChannelRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Contact findContactByWaId(String id) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_CONTACT_BY_ID)) {
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

    public UUID findChannelIdByWaId(String id) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_CHANNEL_BY_ID)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return UUID.fromString(resultSet.getString("id"));
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
        return null;
    }

    // TODO Using waId instead of an object. But WhatsappMessage object would be
    // missleading. Better create a dedicated class for wa channel
    public UUID insert(Contact contact, String waId) {

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setObject(1, contact.getId());
            statement.setInt(2, ChannelType.WHATSAPP.id);
            statement.setString(3, waId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    String id = (generatedKeys.getString(1));

                    return UUID.fromString(id);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.out.print(e);
            throw new PersistenceException(e);
        }
    }
}
