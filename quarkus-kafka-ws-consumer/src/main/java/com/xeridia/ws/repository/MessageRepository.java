package com.xeridia.ws.repository;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.xeridia.ws.config.ScyllaClusterConfig;
import com.xeridia.ws.model.Message;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MessageRepository {

    private final Session session;
    private final PreparedStatement preparedStatement;

    public MessageRepository(ScyllaClusterConfig scyllaClusterConfig) {
        session = scyllaClusterConfig.buildCluster().connect(scyllaClusterConfig.getKeyspace());
        preparedStatement = session.prepare("INSERT INTO message (id, product, price) VALUES (:id, :product, :price)");
    }

    public void save(Message message) {
        BoundStatement bs = preparedStatement
                .bind()
                .setString("id", message.getId().toString())
                .setString("product", message.getProduct())
                .setDouble("price", message.getPrice());
        session.execute(bs);
    }
}
