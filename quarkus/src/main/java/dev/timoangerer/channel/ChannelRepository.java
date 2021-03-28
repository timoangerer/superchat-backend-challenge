package dev.timoangerer.channel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javax.sql.DataSource;


import javax.enterprise.context.ApplicationScoped;

import dev.timoangerer.core.db.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.sql.Statement;

@ApplicationScoped
public class ChannelRepository {
    
    private final DataSource dataSource;

    private final String FIND_CHANNEL_TYPE_BY_CHANNEL_ID = "select channels.channel_type_id from channels where channels.id = ?";

    ChannelRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ChannelType findChannelTypeByChannelId(UUID channelId) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_CHANNEL_TYPE_BY_CHANNEL_ID)) {
            statement.setObject(1, channelId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return ChannelType.valueOfLabel(resultSet.getInt("channel_type_id"));
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
        return null;
    }
}
