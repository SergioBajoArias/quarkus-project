package com.xeridia.ws.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.xeridia.ws.model.Message;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.reactivestreams.FlowAdapters;

import java.time.Duration;
import java.util.Objects;

@ApplicationScoped
public class MessageRepository {

    private final PreparedStatement insertPreparedStatement;

    private final PreparedStatement selectPreparedStatement;

    private final CqlSession cqlSession;

    public MessageRepository() {
        cqlSession = CqlSession.builder()
                .withConfigLoader(
                        DriverConfigLoader.programmaticBuilder()
                                .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofSeconds(15))
                                .build())
                .withKeyspace("catalog").build();
        insertPreparedStatement = cqlSession.prepare("INSERT INTO message (id, product, price) VALUES (:id, :product, :price)");
        selectPreparedStatement = cqlSession.prepare("SELECT * FROM message WHERE ID = :id");

    }

    public void save(Message message) throws InterruptedException {
        BoundStatement bs = insertPreparedStatement
                .bind()
                .setInt("id", message.getId())
                .setString("product", message.getProduct())
                .setDouble("price", message.getPrice());
        cqlSession.execute(bs);
    }

    public Message findById(Integer id) {
        BoundStatement bs = selectPreparedStatement.bind().setInt("id", id);

        ResultSet resultSet = cqlSession.execute(bs);
        Row row = resultSet.one();

        return new Message(row.getInt("id"), row.getString("product"), row.getDouble("price"));
    }

    public Long count() {
        ResultSet resultSet = cqlSession.execute("SELECT COUNT(1) FROM message");
        return Objects.requireNonNull(resultSet.one()).getLong(0);
    }

    public Uni<Long> countReactive() {
        return Uni.createFrom()
                .publisher(FlowAdapters.toFlowPublisher(cqlSession.executeReactive("SELECT COUNT(1) FROM message")))
                .map(row -> row.getLong(0));
    }

    public Uni<Message> findByIdReactive(Integer id) {
        BoundStatement bs = selectPreparedStatement.bind().setInt("id", id);

        return Uni.createFrom()
                .publisher(FlowAdapters.toFlowPublisher(cqlSession.executeReactive(bs)))
                .map(row -> new Message(row.getInt("id"), row.getString("product"), row.getDouble("price")));
    }
}
