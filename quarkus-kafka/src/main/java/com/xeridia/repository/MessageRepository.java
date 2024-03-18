package com.xeridia.repository;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.xeridia.config.ScyllaClusterConfig;
import com.xeridia.model.Message;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MessageRepository {

    private final Session session;
    private final PreparedStatement preparedStatement;

    public MessageRepository(ScyllaClusterConfig scyllaClusterConfig) {
        session = scyllaClusterConfig.buildCluster().connect("catalog");
        preparedStatement = session.prepare("INSERT INTO message (id, product, price) VALUES (?, ?, ?)");
    }

    public void save(Message message) {
        BoundStatement bs = preparedStatement.bind(message.getUuid().toString(), message.getProduct(), message.getPrice());
        session.execute(bs);
    }
}
