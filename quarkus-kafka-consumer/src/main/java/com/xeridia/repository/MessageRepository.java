package com.xeridia.repository;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.xeridia.config.ScyllaClusterConfig;
import com.xeridia.model.Message;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class MessageRepository {

    private final Session session;
    private final PreparedStatement preparedStatement;

    public MessageRepository(ScyllaClusterConfig scyllaClusterConfig) {
        session = scyllaClusterConfig.buildCluster().connect(scyllaClusterConfig.getKeyspace());
        preparedStatement = session.prepare("INSERT INTO message (id, product, price) VALUES (?, ?, ?)");
    }

    public void save(Message message) {
        BoundStatement bs = preparedStatement.bind(message.getUuid().toString(), message.getProduct(), message.getPrice());
        session.execute(bs);
    }

    public Long count() {
        ResultSet resultSet = session.execute("SELECT COUNT(1) FROM message");
        return resultSet.one().getLong("COUNT");
    }
}
