package org.acme;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class User extends PanacheEntity {
    private String name;

    public User() {
    }

    public String getName() {
        return name;
    }

    public User(String name) {
        this.name = name;
    }
}
