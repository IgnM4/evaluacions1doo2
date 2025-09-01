package com.dqr.exps1s3.model;

import java.util.Objects;

public final class User {
    private final String id;
    private final String fullName;
    private final String email;

    public User(String id, String fullName, String email) {
        this.id = Objects.requireNonNull(id);
        this.fullName = Objects.requireNonNull(fullName);
        this.email = Objects.requireNonNull(email);
    }

    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
}
