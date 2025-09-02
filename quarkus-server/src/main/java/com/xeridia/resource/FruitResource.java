package com.xeridia.resource;

import com.xeridia.model.Fruit;
import io.quarkus.hibernate.reactive.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;

    @ResourceProperties(hal = true, path = "/api/admin/fruits")
    public interface FruitResource extends PanacheEntityResource<Fruit, Long> {
    }
