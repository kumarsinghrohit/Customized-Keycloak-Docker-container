package com.auth.extensions.userregistration;

import java.io.Serializable;
import java.util.Objects;

class RegisteredUser implements Serializable {

    private String id;
    private String email;

    RegisteredUser(String id, String email) {
        this.id = Objects.requireNonNull(id);
        this.email = Objects.requireNonNull(email);
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisteredUser that = (RegisteredUser) o;
        return id.equals(that.id) &&
                email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "RegisteredUser {" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
